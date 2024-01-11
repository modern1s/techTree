package com.sparta.techTree.like.service

import com.sparta.techTree.exception.ModelNotFoundException
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.model.Like
import com.sparta.techTree.like.model.toResponse
import com.sparta.techTree.like.repository.LikeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class LikeServiceImpl(private val likeRepository: LikeRepository) : LikeService {
    @Transactional
    override fun toggleLikeForPost(postId: Long, userId: Long): PostLikeResponse {
        //좋아요가 있나 확인
        val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
//            ?: throw ModelNotFoundException("Like", postId) 예외처리중 오류나서 해결중
        //있을경우(if) 좋아요를 토글해서 저장
        if (existingLike != null) {
            existingLike.liked = !existingLike.liked
            val updatedLike = likeRepository.save(existingLike)
            return updatedLike.toResponse()
            //없을경우 좋아요를 최초 생성하여 true(좋아요 상태)로 만듬
        } else {
            val newLike = likeRepository.save(Like(postId = postId, userId = userId, commentId = null, liked = true))
            return newLike.toResponse()
        }
    }
}