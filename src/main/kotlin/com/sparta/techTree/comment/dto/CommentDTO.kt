package com.sparta.techTree.comment.dto


data class CommentDTO (
    val userId: Long,
    val postId: Long,
    val content: String
)