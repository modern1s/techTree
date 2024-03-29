package com.sparta.techTree.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val countLikes: Int
)
