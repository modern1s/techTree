package com.sparta.techTree.like.dto

data class PostLikeResponse(
    val postId: Long,
    val userId: Long,
    val liked: Boolean
)