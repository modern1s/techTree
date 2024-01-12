package com.sparta.techTree.post.dto

import com.sparta.techTree.like.dto.PostLikeResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val userId: Long?,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val countLikes: Int
)
