package com.sparta.techTree.like.service

import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.repository.CommentRepository
import com.sparta.techTree.common.exception.ModelNotFoundException
import com.sparta.techTree.like.dto.CommentLikeResponse
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.like.model.toCommentLikeResponse
import com.sparta.techTree.like.model.toPostLikeResponse
import com.sparta.techTree.like.repository.LikeRepository
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.repository.PostRepository
import com.sparta.techTree.user.model.UserEntity
import com.sparta.techTree.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) : LikeService {

    @Transactional
    override fun createLikeForPost(postId: Long, userId: Long): PostLikeResponse {
            val post: Post = postRepository.findByIdOrNull(postId)
                ?: throw ModelNotFoundException("Post", postId)
            val user: UserEntity = userRepository.findByIdOrNull(userId)
                ?: throw ModelNotFoundException("User", userId)
            val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
            if (existingLike == null) {
                val newLike = likeRepository.save(Like(post = post, user = user, comment = null, liked = true))
                return newLike.toPostLikeResponse()
            } else {
                throw IllegalArgumentException("Like already exists for postId = $postId and userId = $userId")
            }
    }

    @Transactional
    override fun deleteLikeForPost(postId: Long, userId: Long) {
        val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
        if (existingLike != null) {
            likeRepository.delete(existingLike)
        } else {
            throw ModelNotFoundException("Like", "postId = $postId, userId = $userId")
        }
    }

    @Transactional
    override fun createLikeForComment(commentId: Long, userId: Long): CommentLikeResponse {
        val comment: Comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        val user: UserEntity = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
            val existingLike = likeRepository.findByCommentIdAndUserId(commentId, userId)
            if (existingLike == null) {
                val newLike = likeRepository.save(Like(post = null, comment = comment, user = user, liked = true))
                return newLike.toCommentLikeResponse()
            } else {
                throw IllegalArgumentException("Like already exists for commentId = $commentId and userId = $userId")
            }
    }

    @Transactional
    override fun deleteLikeForComment(commentId: Long, userId: Long) {
        val existingLike = likeRepository.findByCommentIdAndUserId(commentId, userId)
        if (existingLike != null) {
            likeRepository.delete(existingLike)
        } else {
            throw ModelNotFoundException("Like", "commentId = $commentId, userId = $userId")
        }
    }

}
