package com.sparta.techTree.user.dto

import com.sparta.techTree.user.model.UserEntity

data class SignUpRequest (
    var email: String,
    val password: String,
    val name: String,
    val birth: String,
    val nickname: String,
    val techStack: String
){
    fun toEntity(): UserEntity =
        UserEntity(email, password,name, birth, nickname,techStack)
}