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

    //이메일을 통해 글을 쓸수 있게 변경
    //id로 할수도 있지만, 그 경우 구분력이 너무 떨어짐(내가 id번호가 몇인지 기억을 해야함)
    //그에 비해서 email은 로그인할때 id역할도 해서 글 작성시 요구했음!
    @Transactional
    override fun createPost(request: CreatePostRequest): PostResponse {
        val user = userRepository.findByEmail(request.userEmail)
            ?: throw EntityNotFoundException("User with email ${request.userEmail} not found")
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
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val countLikes = likeRepository.countByPostId(postId)
        post.title = request.title ?: post.title
        post.content = request.content ?: post.content
        post.countLikes = countLikes
        return post.toResponse()
    }

    @Transactional
    override fun deletePost(postId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        postRepository.delete(post)
    }
}