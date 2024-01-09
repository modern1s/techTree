package com.sparta.techTree.comment.controller

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(private val commentService: CommentService) {

    @GetMapping("/{postId}")
    fun getCommentsByPost(@PathVariable postId: Int): ResponseEntity<List<CommentDTO>> {
        val comments = commentService.getCommentsByPost(postId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping
    fun createComment(@RequestBody commentDto: CommentDTO): ResponseEntity<CommentDTO> {
        val createdComment = commentService.createComment(commentDto)
        return ResponseEntity.ok(createdComment)
    }
}