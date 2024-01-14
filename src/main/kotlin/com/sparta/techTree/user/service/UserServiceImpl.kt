package com.sparta.techTree.user.service

import com.sparta.techTree.common.auth.JwtTokenProvider
import com.sparta.techTree.common.auth.TokenInfo
import com.sparta.techTree.common.exception.InvalidInputException
import com.sparta.techTree.user.dto.*
import com.sparta.techTree.user.model.*
import com.sparta.techTree.user.repository.UserRepository
import com.sparta.techTree.user.repository.UserRoleRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) : UserService {

    override fun signUp(signUpRequest: SignUpRequest): String {
        var user: UserEntity? = userRepository.findByEmail(signUpRequest.email)
        if (user != null) {
            throw InvalidInputException("email", "이미 등록된 email 입니다.")
        }

        user = UserEntity(
            signUpRequest.email,
            createDelegatingPasswordEncoder().encode(signUpRequest.password),
            signUpRequest.name,
            signUpRequest.birth.toString(),
            signUpRequest.nickname,
            signUpRequest.techStack
        )

        userRepository.save(user)

        //권한 저장
        val userRole = UserRoleEntity(null, UserRole.MEMBER, user)
        userRoleRepository.save(userRole)

        return "회원가입 완료"
    }

    //로그인 토큰 발생
    override fun login(loginRequest: LoginRequest): TokenInfo {
        val userInfo = userRepository.findByEmail(loginRequest.email)
            ?: throw InvalidInputException("email", "이메일(${loginRequest.email})이 존재하지 않습니다.")

        val isValidPassword = createDelegatingPasswordEncoder().matches(loginRequest.password, userInfo.password)
        if (!isValidPassword) {
            throw BadCredentialsException("잘못된 패스워드입니다.")
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(userInfo.email, userInfo.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)

    }


    //내 정보 조회
    override fun searchMyInfo(email: String): UserResponse {
        val user: UserEntity = userRepository.findByEmail(email)
            ?: throw InvalidInputException("email", "이메일(${email})이 존재하지 않습니다.")
        return user.toResponse()
    }

    //내 정보 수정
    @Transactional
    override fun saveMyInfo(infoRequest: InfoRequest): String {
        val user = userRepository.findByEmail(infoRequest.email)
            ?: throw InvalidInputException("email", "이메일(${infoRequest.email})이 존재하지 않습니다.")

        if (infoRequest.password != infoRequest.passwordConfirm) {
            throw InvalidInputException("다른 비밀번호를 입력하셨습니다")
        }

        user.password = createDelegatingPasswordEncoder().encode(infoRequest.password) ?: user.password
        user.name = infoRequest.name ?: user.name
        user.nickname = infoRequest.nickname ?: user.nickname
        user.birth = infoRequest.birth ?: user.birth
        user.techStack = infoRequest.techStack ?: user.techStack

        user.toResponse()
        return "수정되었습니다."
    }


    // 회원 탈퇴
    @Transactional
    override fun deleteMyInfo(userId: Long): String {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("해당 ID를 가진 사용자를 찾을 수 없습니다.")
        }

        userRepository.delete(user)
        return "삭제되었습니다."
    }
}