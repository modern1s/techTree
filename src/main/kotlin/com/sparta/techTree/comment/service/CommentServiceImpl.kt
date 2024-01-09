package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.repository.CommentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentServiceImpl (private val commentRepository: CommentRepository) : CommentService {
    override fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentDTO {
        val commentToUpdate = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to update")
        commentToUpdate.content = request.content

        // 업데이트된 댓글 저장
        commentRepository.save(commentToUpdate)

        // 업데이트된 댓글 정보를 DTO로 변환하여 반환
        return CommentDTO(
            id = commentId,
            postId = commentToUpdate.postId,
            userId = commentToUpdate.userId,
            content = commentToUpdate.content,
            createdAt = commentToUpdate.createdAt,
            updatedAt = commentToUpdate.updatedAt
        )
    }


    override fun deleteComment(commentId: Long, userId: Long)  {
        val deleteComment = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to delete")
        commentRepository.delete(deleteComment)

    override fun getCommentsByPost(postId: Int): List<CommentDTO> {
        val comments = commentRepository.findByPostId(postId)
        return comments.map { CommentDTO(it.id, it.userId, it.postId, it.content, it.createdAt, it.updatedAt, it.deletedAt) }
    }

    @Transactional
    override fun createComment(commentDto: CommentDTO): CommentDTO {
        val comment = Comment(userId = commentDto.userId, postId = commentDto.postId, content = commentDto.content)
        val savedComment = commentRepository.save(comment)
        return CommentDTO(savedComment.id, savedComment.userId, savedComment.postId, savedComment.content, savedComment.createdAt, savedComment.updatedAt, savedComment.deletedAt)
    }
        }
    }
