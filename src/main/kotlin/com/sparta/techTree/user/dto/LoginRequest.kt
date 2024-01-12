package com.sparta.techTree.user.dto

data class LoginRequest (
    val email: String,
    var password: String
)