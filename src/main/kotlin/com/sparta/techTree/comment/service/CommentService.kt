package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment

interface CommentService {
    fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentResponse

    fun deleteComment(commentId: Long, userId: Long)

    fun getCommentsByPost(postId: Long): List<CommentResponse>
    //postid를 가져와서 댓글을 만들게 변경
    fun createComment(postId: Long, request: CreateCommentRequest) : Comment

}
