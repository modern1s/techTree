package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CreateCommentRequest
import com.sparta.techTree.comment.dto.CommentResponse
import com.sparta.techTree.comment.dto.UpdateCommentRequest
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(private val commentService: CommentService) {

    @GetMapping("/{postId}")
    fun getCommentsByPost(@PathVariable postId: Long): ResponseEntity<List<CommentResponse>> {
        val comments = commentService.getCommentsByPost(postId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping("/{postId}")
    fun createComment(
        @PathVariable postId: Long, @RequestBody createCommentRequest: CreateCommentRequest): ResponseEntity<CommentResponse> {
        val createdComment: Comment = commentService.createComment(postId,createCommentRequest)
        //이 부분을 Post의 Model처럼 toResponse를 만들어서 클린코딩 하면 좋을것 같으나 일단 이렇게 함
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
}