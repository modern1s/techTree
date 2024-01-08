package com.sparta.techTree.post.dto

data class UpdatePostRequest(
    val id: Long,
    val title: String,
    val content: String
)
