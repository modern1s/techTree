package com.sparta.techTree.post.service

import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.model.toResponse
import com.sparta.techTree.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import com.sparta.techTree.exception.ModelNotFoundException


@Service

class PostServiceImpl (private val postRepository : PostRepository) : PostService {
    override fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map { it.toResponse()}
    }

    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return post.toResponse()
    }

    @Transactional
    override fun createPost(request: CreatePostRequest) : PostResponse{
        return postRepository.save(
            Post(
                title = request.title,
                content = request.content
            )
        ).toResponse()
    }
}