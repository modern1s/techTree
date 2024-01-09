package com.sparta.techTree.comment.dto

import java.time.LocalDateTime

data class CommentDTO (
    val id: Int,
    val userId: Int,
    val postId: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
)