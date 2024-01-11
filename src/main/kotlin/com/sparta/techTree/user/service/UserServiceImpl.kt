package com.sparta.techTree.user.service

import com.sparta.techTree.common.auth.JwtTokenProvider
import com.sparta.techTree.common.auth.TokenInfo
import com.sparta.techTree.common.exception.InvalidInputException
import com.sparta.techTree.user.dto.LoginRequest
import com.sparta.techTree.user.dto.SignUpRequest
import com.sparta.techTree.user.dto.UserResponse
import com.sparta.techTree.user.model.*
import com.sparta.techTree.user.repository.UserRepository
import com.sparta.techTree.user.repository.UserRoleRepository
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

        //사용자 정보 저장
//        member = memberDtoRequest.toEntity()

        user = UserEntity(
            signUpRequest.email,
            createDelegatingPasswordEncoder().encode(signUpRequest.password),
            signUpRequest.name,
            signUpRequest.birth,
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
        loginRequest.password = createDelegatingPasswordEncoder().encode(loginRequest.password)

//        fun checkPassword(inputPassword: String, storedHashedPassword: String): Boolean {
//            return createDelegatingPasswordEncoder().matches(inputPassword, storedHashedPassword)

        val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtTokenProvider.createToken(authentication)

    }


    //내 정보 조회
    override fun searchMyInfo(email: String): UserResponse {
        val user = userRepository.findByEmail(email)
            ?: throw InvalidInputException("email", "이메일(${email})이 존재하지 않습니다.")
        return user.toResponse()
    }

    //내 정보 수정
    override fun saveMyInfo(signUpRequest: SignUpRequest): String {
        val user = signUpRequest.toEntity()
        userRepository.save(user)
        return "수정되었습니다."
    }
}