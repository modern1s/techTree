package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.dto.UpdateCommentRequest

interface CommentService {
    fun updateComment(commentId: Long, userId:Long, request: UpdateCommentRequest): CommentDTO

    fun deleteComment(commentId: Long, userId:Long)

    fun getCommentsByPost(postId: Int): List<CommentDTO>

    fun createComment(commentDto: CommentDTO): CommentDTO

}
