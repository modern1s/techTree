package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(private val commentRepository: CommentRepository) : CommentService {

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