package com.sparta.techTree.comment.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sparta.techTree.comment.dto.CommentDTO
import com.sparta.techTree.comment.service.CommentService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import org.mockito.kotlin.any
@ExtendWith(SpringExtension::class)
@WebMvcTest(CommentController::class)
class CommentControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var commentDTO: CommentDTO

    @BeforeEach
    fun setup() {
        commentDTO = CommentDTO(1, 1, 1, "Sample comment", LocalDateTime.parse("2024-01-09T20:45:55"), null, null)
    }

    @Test
    fun `get comments by post id`() {
        `when`(commentService.getCommentsByPost(anyInt())).thenReturn(listOf(commentDTO))

        mockMvc.perform(get("/comments/{postId}", 1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].content").value("Sample comment"))
    }

    @Test
    fun `create a new comment`() {
        `when`(commentService.createComment(any())).thenReturn(commentDTO)

        mockMvc.perform(post("/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(commentDTO)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").value("Sample comment"))
    }
}