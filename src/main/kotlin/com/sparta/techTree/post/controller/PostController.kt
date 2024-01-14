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
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
    fun createPost(@AuthenticationPrincipal user: CustomUser, @RequestBody createPostRequest: CreatePostRequest): ResponseEntity<PostResponse> {
        val userId = user.id
        val postResponse = postService.createPost(createPostRequest, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse)
    }

    //@pathvariable에 userid는 필요없음,userid를 직접 적어서 하는게 아니고 로그인 되있는 아이디를 사용해야 하기 때문
    @PatchMapping("/{postId}")
    fun updatePost(@AuthenticationPrincipal user: CustomUser,
        @PathVariable postId: Long,updatePostRequest: UpdatePostRequest): ResponseEntity<PostResponse> {
        val userId = user.id
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, userId, updatePostRequest))
    }
    //@pathvariable에 userid는 필요없음,userid를 직접 적어서 하는게 아니고 로그인 되있는 아이디를 사용해야 하기 때문
    @DeleteMapping("/{postId}")
    fun deletePost(@AuthenticationPrincipal user: CustomUser, @PathVariable postId: Long): ResponseEntity<Unit> {
        val userId = user.id
        postService.deletePost(postId, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
    //{userid}는 적을 필요가 없음/@pathvariable에 userid는 필요없음,userid를 직접 적어서 하는게 아니고 로그인 되있는 아이디를 사용해야 하기 때문
    @PostMapping("/likes/{postId}")
    fun createLikeForPost(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable postId: Long
    ): ResponseEntity<PostLikeResponse> {
        val userId = user.id
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLikeForPost(postId, userId))
    }
    //{userid}는 적을 필요가 없음/@pathvariable에 userid는 필요없음,userid를 직접 적어서 하는게 아니고 로그인 되있는 아이디를 사용해야 하기 때문
    @DeleteMapping("/likes/{postId}")
    fun deleteLikeForPost(
        @AuthenticationPrincipal user: CustomUser,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        val userId = user.id
        likeService.deleteLikeForPost(postId, userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}
