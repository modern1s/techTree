package com.sparta.techTree.post.service

import com.sparta.techTree.common.exception.ModelNotFoundException
import com.sparta.techTree.post.dto.UpdatePostRequest
import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import com.sparta.techTree.post.model.Post
import com.sparta.techTree.post.model.toResponse
import com.sparta.techTree.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import com.sparta.techTree.like.repository.LikeRepository
import com.sparta.techTree.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import java.nio.file.AccessDeniedException


@Service

class PostServiceImpl(private val postRepository: PostRepository, private val likeRepository: LikeRepository,private val userRepository: UserRepository) :
    PostService {


    override fun getPostList(): List<PostResponse> {
        return postRepository.findAll().map { post ->
            val countLikes = likeRepository.countByPostId(post.id!!)
            post.countLikes = countLikes
            post.toResponse()
        }
    }

    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val countLikes = likeRepository.countByPostId(postId)
        post.countLikes = countLikes

        return post.toResponse()
    }


    override fun createPost(request: CreatePostRequest, userId: Long): PostResponse {
        val user = userRepository.findById(userId)
            .orElseThrow {
                EntityNotFoundException("User with ID $userId not found")
            }
        val createdPost = postRepository.save(
            Post(
                title = request.title,
                content = request.content,
                user = user,
                countLikes = 0
            )
        )
        return createdPost.toResponse()
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest,userId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post.user.id != userId) {
            throw AccessDeniedException("User with ID $userId does not have permission to update post with ID $postId")
        }
        val countLikes = likeRepository.countByPostId(postId)
        post.title = request.title ?: post.title
        post.content = request.content ?: post.content
        post.countLikes = countLikes
        val updatedPost = postRepository.save(post)
        return updatedPost.toResponse()
    }

    @Transactional
    override fun deletePost(postId: Long,userId:Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post.user.id != userId) {
            throw AccessDeniedException("User with ID $userId does not have permission to delete post with ID $postId")
        }
        postRepository.delete(post)
    }
}