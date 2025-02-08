package com.example.personalwebsite.controller;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class) // 測試 CommentController
@ExtendWith(MockitoExtension.class)  // 使用 Mockito
class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService; // 模擬 Service 層

    @InjectMocks
    private CommentController commentController; // 注入 Mock Service

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void shouldReturnAllComments() throws Exception {
        List<Comment> comments = Arrays.asList(
                new Comment(1L, "Joe", "joe@example.com", "這是一則留言"),
                new Comment(2L, "Alice", "alice@example.com", "這是另一則留言")
        );

        when(commentService.getAllComments()).thenReturn(comments);

        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isOk()) // 期望 HTTP 200
                .andExpect(jsonPath("$.length()").value(2)) // 期望回傳 2 筆留言
                .andExpect(jsonPath("$[0].name").value("Joe"))
                .andExpect(jsonPath("$[1].name").value("Alice"));

        verify(commentService, times(1)).getAllComments(); // 驗證是否被呼叫一次
    }
}
