package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.service.CommentService
import com.sparta.techTree.like.dto.PostLikeResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(private val commentService: CommentService) {
    //TODO:댓글을 상세조회할때 좋아요 갯수가 몇개인지 확인할수 있게
    @GetMapping("/{postId}")
    fun getCommentsByPost(@PathVariable postId: Long): ResponseEntity<List<CommentResponse>> {
        val comments = commentService.getCommentsByPost(postId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping("/{postId}")
    fun createComment(
        @PathVariable postId: Long, @RequestBody createCommentRequest: CreateCommentRequest): ResponseEntity<CommentResponse> {
        val createdComment: Comment = commentService.createComment(postId,createCommentRequest)
        val commentResponse = CommentResponse(
            id = createdComment.id!!,
            content = createdComment.content,
            userId = createdComment.userId,
            postId = createdComment.post.id!!,
            createdAt = createdComment.createdAt,
            updatedAt = createdComment.updatedAt
        )
        return ResponseEntity.ok(commentResponse)
    }
    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestHeader userId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ):  ResponseEntity<CommentResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId,userId, updateCommentRequest))

    }
    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long,@RequestHeader userId: Long,):ResponseEntity<Unit>
    {
        commentService.deleteComment(commentId, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
    //TODO Post와 마찬가지 로직으로 comment마다 좋아요가 가능하게 변경.
    @PatchMapping("/comment/{commentId}")
    fun toggleLikeForComment(@PathVariable commentId: Long, @RequestHeader userId: Long):ResponseEntity<PostLikeResponse> {
        TODO("postId에 생성된 좋아요를 userId마다 토글기능")
    }
}