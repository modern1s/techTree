package com.sparta.techTree.user.dto

import com.sparta.techTree.user.model.UserEntity
import jakarta.validation.constraints.Email

data class InfoRequest(
    var email: String,
    var password: String?,
    var passwordConfirm: String?,
    var name: String?,
    var birth: String?,
    var nickname: String?,
    var techStack: String?
)