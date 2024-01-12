package com.sparta.techTree.comment.dto

data class CreateCommentRequest (
    val userId: Long,
    val content: String
)