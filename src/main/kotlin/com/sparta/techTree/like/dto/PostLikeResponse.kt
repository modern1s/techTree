package com.sparta.techTree.like.dto

data class PostLikeResponse(
    val userId: Long,
    val liked: Boolean,
)