package com.sparta.techTree.user.controller

import com.sparta.techTree.common.auth.TokenInfo
import com.sparta.techTree.common.dto.BaseResponse
import com.sparta.techTree.common.dto.CustomUser
import com.sparta.techTree.user.dto.LoginRequest
import com.sparta.techTree.user.dto.SignUpRequest
import com.sparta.techTree.user.dto.UserResponse
import com.sparta.techTree.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/mypage")
@RestController
class UserController (
    private val userService: UserService
){
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid signUpRequest: SignUpRequest): BaseResponse<Unit>{
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
    @PutMapping("/info")
    fun saveMyInfo(@RequestBody @Valid signupRequest: SignUpRequest):
            BaseResponse<Unit> {
        val userEmail = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .email
        signupRequest.email = userEmail
        val resultMsg: String = userService.saveMyInfo(signupRequest)
        return BaseResponse(message = resultMsg)
    }
}