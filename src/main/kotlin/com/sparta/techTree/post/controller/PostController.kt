package com.sparta.techTree.post.controller

import com.sparta.techTree.common.dto.CustomUser
import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.service.LikeService
import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse
import com.sparta.techTree.post.dto.UpdatePostRequest
import com.sparta.techTree.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RequestMapping("/post")
@RestController
class PostController(private val postService: PostService,private val likeService: LikeService) {

    @GetMapping
    fun getPostList(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostList())
    }

    @GetMapping("/{postId}")
    fun getPostById(@PathVariable postId: Long): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId))
    }

    @PostMapping
    fun createPost(@RequestBody createPostRequest: CreatePostRequest): ResponseEntity<PostResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .id
        val postResponse = postService.createPost(createPostRequest, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse)
    }


    @PatchMapping("/{postId}")
    fun updatePost(@PathVariable postId: Long, updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .id
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, updatePostRequest , userId))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Unit> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .id
        postService.deletePost(postId,userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PostMapping("/likes/{postId}")
    fun createLikeForPost(@PathVariable postId: Long): ResponseEntity<PostLikeResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .id
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLikeForPost(postId, userId))
    }

    @DeleteMapping("/likes/{postId}")
    fun deleteLikeForPost(@PathVariable postId: Long): ResponseEntity<Unit> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .id
        likeService.deleteLikeForPost(postId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
