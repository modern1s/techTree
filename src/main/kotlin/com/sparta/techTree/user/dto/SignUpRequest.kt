package com.sparta.techTree.user.dto


data class SignUpRequest (
    var email: String,
    val password: String,
    val name: String,
    val birth: String,
    val nickname: String,
    val techStack: String
)