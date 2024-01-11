package com.sparta.techTree.like.service

import com.sparta.techTree.exception.ModelNotFoundException
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.like.model.toResponse
import com.sparta.techTree.like.repository.LikeRepository
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class LikeServiceImpl(private val likeRepository: LikeRepository,private val postRepository:PostRepository) : LikeService {

    @Transactional
    override fun toggleLikeForPost(postId: Long, userId: Long): PostLikeResponse {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post != null) {
            val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
            if (existingLike != null) {
                existingLike.liked = !existingLike.liked
                val updatedLike = likeRepository.save(existingLike)
                return updatedLike.toResponse()
            } else {
                val newLike =
                    likeRepository.save(Like(postId = postId, userId = userId, commentId = null, liked = true))
                return newLike.toResponse()
            }
        } else {
            throw ModelNotFoundException("Post", postId)
        }
    }
}
