package com.sparta.techTree.like.dto

data class CommentLikeResponse(
    val commentId: Long?,
    val userId: Long,
    val liked: Boolean,
)
