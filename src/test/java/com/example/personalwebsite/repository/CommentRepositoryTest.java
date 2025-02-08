package com.example.personalwebsite.repository;

import com.example.personalwebsite.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // 只載入 JPA 相關的元件
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldFindCommentsByName() {
        Comment comment = new Comment();
        comment.setName("Joe");
        comment.setEmail("joe@example.com");
        comment.setComment("測試留言");
        commentRepository.save(comment);

        List<Comment> result = commentRepository.findByName("Joe");

        assertEquals(1, result.size());
        assertEquals("Joe", result.get(0).getName());
    }
}
