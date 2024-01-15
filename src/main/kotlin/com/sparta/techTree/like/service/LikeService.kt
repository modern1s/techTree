package com.sparta.techTree.like.service

import com.sparta.techTree.like.dto.CommentLikeResponse
import com.sparta.techTree.like.dto.PostLikeResponse

interface LikeService {
//    fun createLikeForPost(postId: Long, userId: Long): PostLikeResponse
//
//    fun deleteLikeForPost(postId: Long, userId: Long)
//
//    fun createLikeForComment(commentId: Long, userId: Long): CommentLikeResponse
//
//    fun deleteLikeForComment(commentId: Long, userId: Long)

    fun toggleLikeForPost(postId: Long,userId: Long) : PostLikeResponse

    fun toggleLikeForComment(commentId: Long,userId: Long) :CommentLikeResponse
}
