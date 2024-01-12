package com.sparta.techTree.post.dto

data class CreatePostRequest(
    val title: String,
    val content: String,
    val userEmail: String
)
