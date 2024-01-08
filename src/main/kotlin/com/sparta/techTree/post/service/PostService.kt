package com.sparta.techTree.post.service

import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse

interface PostService {
    fun getPostList(): List<PostResponse>

    fun getPostById(postId: Long): PostResponse

    fun createPost(request: CreatePostRequest) : PostResponse
}