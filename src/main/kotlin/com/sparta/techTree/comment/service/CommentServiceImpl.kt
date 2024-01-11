package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.repository.CommentRepository
import com.sparta.techTree.exception.ModelNotFoundException
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository, private val postRepository: PostRepository
) : CommentService {

    override fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentResponse {
        val commentToUpdate = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to update")
        commentToUpdate.content = request.content

        commentRepository.save(commentToUpdate)
        return CommentResponse(
            id = commentToUpdate.id!!,
            userId = commentToUpdate.userId,
            postId = commentToUpdate.post.id!!,
            content = commentToUpdate.content,
            createdAt = commentToUpdate.createdAt,
            updatedAt = commentToUpdate.updatedAt
        )
    }


    override fun deleteComment(commentId: Long, userId: Long) {
        val deleteComment = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to delete")
        commentRepository.delete(deleteComment)
    }

    override fun getCommentsByPost(postId: Long): List<CommentResponse> {
        val comments = commentRepository.findByPostId(postId)
        return comments.map {
            CommentResponse(
                it.id!!,
                it.userId,
                it.post.id!!,
                it.content,
                it.createdAt,
                it.updatedAt
            )
        }
    }


    @Transactional
    override fun createComment(postId: Long, request: CreateCommentRequest): Comment {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = Comment(
            post = post, userId = request.userId, content = request.content
        )

        return commentRepository.save(comment)

    }
}

