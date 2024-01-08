package com.sparta.techTree.post.controller

import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse
import com.sparta.techTree.post.dto.UpdatePostRequest
import com.sparta.techTree.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/post")
@RestController
class PostController(private val postService: PostService) {

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
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest))
    }

    @PatchMapping("/{postId}")
    fun updatePosts(@PathVariable postId: Long, updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId,updatePostRequest))
    }

}
