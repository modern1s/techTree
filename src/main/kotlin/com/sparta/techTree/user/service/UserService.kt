package com.sparta.techTree.user.service

import com.sparta.techTree.common.auth.TokenInfo
import com.sparta.techTree.user.dto.InfoRequest
import com.sparta.techTree.user.dto.LoginRequest
import com.sparta.techTree.user.dto.SignUpRequest
import com.sparta.techTree.user.dto.UserResponse

interface UserService {

    fun signUp(signUpRequest: SignUpRequest): String

    fun login(loginRequest: LoginRequest): TokenInfo

    fun searchMyInfo(email: String): UserResponse

    fun saveMyInfo(infoRequest: InfoRequest): String

    fun deleteMyInfo(userId: Long): String
}