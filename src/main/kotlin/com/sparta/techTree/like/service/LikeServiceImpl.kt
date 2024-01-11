package com.sparta.techTree.like.service

import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.repository.CommentRepository
import com.sparta.techTree.exception.ModelNotFoundException
import com.sparta.techTree.like.dto.CommentLikeResponse
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.like.model.toCommentLikeResponse
import com.sparta.techTree.like.model.toPostLikeResponse
import com.sparta.techTree.like.repository.LikeRepository
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) : LikeService {
    //patch 기능으로 도저히 좋아요 숫자 증감 못하겠어서 delete랑 create로 나눳습니다...
    //create로 좋아요를 만들고, delete로 삭제합니다
    @Transactional
    override fun createLikeForPost(postId: Long, userId: Long): PostLikeResponse {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post != null) {
            val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
            if (existingLike == null) {
                val newLike = likeRepository.save(Like(postId = postId, userId = userId, commentId = null, liked = true))
                return newLike.toPostLikeResponse()
            } else {
                throw IllegalArgumentException("Like already exists for postId = $postId and userId = $userId")
            }
        } else {
            throw ModelNotFoundException("Post", postId)
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
        if (comment != null) {
            val existingLike = likeRepository.findByCommentIdAndUserId(commentId, userId)
            if (existingLike == null) {
                val newLike = likeRepository.save(Like(postId = null, commentId = commentId, userId = userId, liked = true))
                return newLike.toCommentLikeResponse()
            } else {
                throw IllegalArgumentException("Like already exists for commentId = $commentId and userId = $userId")
            }
        } else {
            throw ModelNotFoundException("Comment", commentId)
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
