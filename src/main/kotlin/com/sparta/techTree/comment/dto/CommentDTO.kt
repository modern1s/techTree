package com.sparta.techTree.comment.dto

import java.time.LocalDateTime

data class CommentDTO (
    val id: Long?,
    val userId: Long,
    val postId: Long,
    val content: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
)