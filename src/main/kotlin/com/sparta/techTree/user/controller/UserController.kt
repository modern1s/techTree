package com.sparta.techTree.user.controller

import com.sparta.techTree.common.auth.TokenInfo
import com.sparta.techTree.common.dto.BaseResponse
import com.sparta.techTree.common.dto.CustomUser
import com.sparta.techTree.user.dto.InfoRequest
import com.sparta.techTree.user.dto.LoginRequest
import com.sparta.techTree.user.dto.SignUpRequest
import com.sparta.techTree.user.dto.UserResponse
import com.sparta.techTree.user.service.UserService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid signUpRequest: SignUpRequest): BaseResponse<Unit> {
        val resultMsg: String = userService.signUp(signUpRequest)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginRequest: LoginRequest): BaseResponse<TokenInfo> {
        val tokenInfo = userService.login(loginRequest)
        return BaseResponse(data = tokenInfo)
    }

    // 내 정보 조회
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<UserResponse> {
        val userEmail = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .email
        val response = userService.searchMyInfo(userEmail)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 저장
     */
    @PatchMapping("/info")
    fun saveMyInfo(@RequestBody @Valid infoRequest: InfoRequest):
            BaseResponse<Unit> {
        val userEmail = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .email
        infoRequest.email = userEmail
        val resultMsg: String = userService.saveMyInfo(infoRequest)
        return BaseResponse(message = resultMsg)
    }

    // 회원 탈퇴
    @DeleteMapping("/info")
    fun deleteMyInfo(): BaseResponse<UserResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .id
        val resultMsg: String = userService.deleteMyInfo(userId)
        return BaseResponse(message = resultMsg)
    }
}