package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.model.toResponse
import com.sparta.techTree.comment.repository.CommentRepository
import com.sparta.techTree.common.exception.ModelNotFoundException
import com.sparta.techTree.like.repository.LikeRepository
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.repository.PostRepository
import com.sparta.techTree.user.model.UserEntity
import com.sparta.techTree.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val userRepository: UserRepository
) : CommentService {
  //comment 같은 경우는 post와 다르게 추가적인 조건 없이도 잘 작동이 됨... 이유는 모르겠음, 연구해봐야 될듯!

    //기존 코드도 문제는 없었지만 user부분 연결하면서 코드 변경 repository에서 id값을 가져옴
    @Transactional
    override fun createComment(postId: Long, userId: Long, request: CreateCommentRequest): CommentResponse {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user: UserEntity = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User",userId)
        val comment = Comment(
            post = post, user = user, content = request.content, countLikes = 0
        )
        val savedComment = commentRepository.save(comment)
        return savedComment.toResponse()
    }

    @Transactional
    override fun updateComment(commentId: Long, userId: Long, request: UpdateCommentRequest): CommentResponse {
        val commentToUpdate = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to update")
        commentToUpdate.content = request.content
        val updatedComment = commentRepository.save(commentToUpdate)
        val countLikes = likeRepository.countByCommentId(commentId)
        commentToUpdate.countLikes = countLikes
        return updatedComment.toResponse()
    }


    @Transactional
    override fun deleteComment(commentId: Long, userId: Long) {
        val deleteComment = commentRepository.findByIdAndUserId(commentId, userId)
            ?: throw IllegalArgumentException("Comment not found or user not authorized to delete")
        commentRepository.delete(deleteComment)
    }

    @Transactional
    override fun getCommentsByPost(postId: Long): List<CommentResponse> {
        val comments = commentRepository.findByPostId(postId)
        return comments.map { comment ->
            val countLikes = likeRepository.countByCommentId(comment.id!!)
            comment.countLikes = countLikes
            comment.toResponse()
        }
    }
}

