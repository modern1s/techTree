package com.sparta.techTree.comment.service

import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.model.Comment
import com.sparta.techTree.comment.repository.CommentRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime

@SpringBootTest
class CommentServiceImplTest {

    @MockBean
    private lateinit var commentRepository: CommentRepository

    @Test
    fun `getCommentsByPost returns list of comments`() {

        val postId = 1
        val comments = listOf(
            Comment(1, 1, postId, "Test content 1", LocalDateTime.now(), null, null),
            Comment(2, 2, postId, "Test content 2", LocalDateTime.now(), null, null)
        )
        val commentService = CommentServiceImpl(commentRepository)


        `when`(commentRepository.findByPostId(postId)).thenReturn(comments)


        val commentDTOs = commentService.getCommentsByPost(postId)


        assertEquals(2, commentDTOs.size)
        assertEquals("Test content 1", commentDTOs[0].content)
    }

    @Test
    fun `createComment saves and returns comment`() {
        val testDateTime = LocalDateTime.parse("2024-01-09T20:45:55")
        val commentDto = CommentDTO(1, 1, 1, "New comment", testDateTime, null, null)
        val savedComment = Comment(1,
            commentDto.userId, commentDto.postId, commentDto.content, LocalDateTime.now(), null, null)
        val commentService = CommentServiceImpl(commentRepository)


        `when`(commentRepository.save(Mockito.any(Comment::class.java))).thenReturn(savedComment)


        val result = commentService.createComment(commentDto)


        assertEquals("New comment", result.content)
    }
}