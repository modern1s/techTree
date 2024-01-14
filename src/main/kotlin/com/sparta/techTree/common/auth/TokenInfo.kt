package com.sparta.techTree.common.auth

data class TokenInfo(
    val grantType: String,
    val accessToken: String,
)