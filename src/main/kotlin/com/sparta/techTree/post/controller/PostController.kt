package com.sparta.techTree.post.controller

import com.sparta.techTree.like.dto.PostLikeResponse
import com.sparta.techTree.like.service.LikeService
import com.sparta.techTree.post.dto.CreatePostRequest
import com.sparta.techTree.post.dto.PostResponse
import com.sparta.techTree.post.dto.UpdatePostRequest
import com.sparta.techTree.post.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest))
    }

    @PatchMapping("/{postId}")
    fun updatePost(@PathVariable postId: Long, updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, updatePostRequest))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Unit> {
        postService.deletePost(postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PostMapping("/likes/{postId}/{userId}")
    fun createLikeForPost(@PathVariable postId: Long, @RequestHeader userId: Long): ResponseEntity<PostLikeResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLikeForPost(postId, userId))
    }

    @DeleteMapping("/likes/{postId}/{userId}")
    fun deleteLikeForPost(@PathVariable postId: Long, @RequestHeader userId: Long): ResponseEntity<Unit> {
        likeService.deleteLikeForPost(postId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
