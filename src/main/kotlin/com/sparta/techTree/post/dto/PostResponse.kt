package com.sparta.techTree.post.dto

import com.sparta.techTree.post.model.BaseTimeEntity
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
