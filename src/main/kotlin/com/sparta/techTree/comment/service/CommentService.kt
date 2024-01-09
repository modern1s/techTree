package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CommentDTO

interface CommentService {

    fun getCommentsByPost(postId: Int): List<CommentDTO>
    fun createComment(commentDto: CommentDTO): CommentDTO
}