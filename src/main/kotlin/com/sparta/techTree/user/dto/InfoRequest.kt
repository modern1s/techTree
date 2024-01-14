package com.sparta.techTree.user.dto

data class InfoRequest(
    var email: String,
    var password: String?,
    var passwordConfirm: String?,
    var name: String?,
    var birth: String?,
    var nickname: String?,
    var techStack: String?
)