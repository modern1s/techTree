package com.sparta.techTree.like.service

import com.sparta.techTree.like.dto.PostLikeResponse

interface LikeService {
    fun toggleLikeForPost(postId: Long, userId: Long): PostLikeResponse
}