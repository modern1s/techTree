package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest

interface CommentService {
    fun createComment(postId: Long, userId: Long, request: CreateCommentRequest): CommentResponse

    fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentResponse

    fun deleteComment(commentId: Long, userId: Long)

    fun getCommentsByPost(postId: Long): List<CommentResponse>

}
