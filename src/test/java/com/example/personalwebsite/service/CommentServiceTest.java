package com.example.personalwebsite.service;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository; // 模擬 Repository 層

    @InjectMocks
    private CommentService commentService; // 測試 Service 層

    @Test
    void shouldReturnAllComments() {
        List<Comment> comments = Arrays.asList(
                new Comment(1L, "Joe", "joe@example.com", "這是一則留言"),
                new Comment(2L, "Alice", "alice@example.com", "這是另一則留言")
        );

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> result = commentService.getAllComments();

        assertEquals(2, result.size());
        assertEquals("Joe", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());

        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnCommentById() {
        Comment comment = new Comment(1L, "Joe", "joe@example.com", "這是一則留言");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Optional<Comment> result = commentService.getCommentById(1L);

        assertTrue(result.isPresent());
        assertEquals("Joe", result.get().getName());

        verify(commentRepository, times(1)).findById(1L);
    }
}
