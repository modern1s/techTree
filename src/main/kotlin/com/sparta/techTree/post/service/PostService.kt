package com.sparta.techTree.post.service

import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse
import com.sparta.techTree.post.dto.UpdatePostRequest

interface PostService {
    fun getPostList(): List<PostResponse>

    fun getPostById(postId: Long): PostResponse

    fun createPost(request: CreatePostRequest,userId: Long): PostResponse

    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse

    fun deletePost(postId: Long)
}