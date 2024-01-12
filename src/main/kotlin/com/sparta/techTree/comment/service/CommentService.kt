package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest

interface CommentService {
    fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentResponse

    fun deleteComment(commentId: Long, userId: Long)

    fun getCommentsByPost(postId: Long): List<CommentResponse>

    fun createComment(commentDto: CommentDTO): CommentResponse

}
