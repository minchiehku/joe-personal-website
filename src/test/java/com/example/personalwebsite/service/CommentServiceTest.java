package com.example.personalwebsite.service;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  CommentServiceTest 測試類別
 *
 *  使用 Mockito 來模擬 CommentRepository，達到單元測試隔離的目的。
 */
@ExtendWith(MockitoExtension.class) // 啟用 Mockito 擴展，讓 @Mock 與 @InjectMocks 自動初始化
class CommentServiceTest {

    // 模擬 CommentRepository 層
    @Mock
    private CommentRepository commentRepository;

    // 模擬 CommentService 層並注入依賴
    @InjectMocks
    private CommentService commentService;

    /**
     * 測試 saveComment 方法: 前端傳入新的留言物件, 呼叫 Repository層的save方法儲存, 成功儲存後回傳存好的物件, 包含自動生成的ID與留言時時間
     * Arrange: 建立一個不帶 id 的 Comment 物件，並模擬 repository.save() 返回一個帶有 id 的 Comment 物件
     * Act: 調用 commentService.saveComment()
     * Assert: 驗證返回的 Comment 物件不為 null、id 與其它欄位值是否符合預期，
     *         同時檢查 commentRepository.save() 方法是否被正確調用一次。
     */
    @Test
    void testSaveComment() {
        // 1. Arrange(安排): 準備一個 "新的留言物件"
        Comment newComment = new Comment(null, "Joe", "joe@example.com", "這是一則留言");
        // 模擬 "儲存後返回的物件" ，假設資料庫自動生成 id 為 1L
        Comment savedComment = new Comment(1L, "Joe", "joe@example.com", "這是一則留言");

        // 當 repository.save() 被呼叫，傳入 newComment 時，返回 savedComment
        when(commentRepository.save(newComment)).thenReturn(savedComment);

        // 2. Act(執行):調用 CommentService 的 saveComment 方法
        Comment result = commentService.saveComment(newComment);

        // 3. Assert(斷言): 驗證結果不為 null，並且 id 與其他欄位符合預期
        assertNotNull(result, "返回的 Comment 不應為 null");
        assertEquals(1L, result.getId(), "Comment 的 id 應為 1");
        assertEquals("Joe", result.getName(), "Comment 的 name 應為 Joe");
        assertEquals("joe@example.com", result.getEmail(), "Comment 的 email 應為 joe@example.com");
        assertEquals("這是一則留言", result.getComment(), "Comment 的內容不符");

        // 驗證 repository.save() 方法確實被呼叫了一次
        verify(commentRepository, times(1)).save(newComment);
    }

    /**
     * 測試 getAllComments 方法
     * Arrange: 建立一個包含兩筆留言的 List，並模擬 repository.findAll() 返回此 List
     * Act: 調用 commentService.getAllComments() 方法
     * Assert: 驗證返回的 List 不為 null、數量與內容是否符合預期，
     *         同時檢查 repository.findAll() 是否被正確調用一次。
     */
    @Test
    void testGetAllComments() {
        // 1. Arrange(安排) : 建立兩個模擬的 Comment 物件
        Comment comment1 = new Comment(1L, "Joe", "joe@example.com", "這是一則留言");
        Comment comment2 = new Comment(2L, "Mike", "mike@example.com", "另一則留言");
        List<Comment> comments = Arrays.asList(comment1, comment2);

        // 當 repository.findAll() 被呼叫時，返回模擬的留言列表
        when(commentRepository.findAll()).thenReturn(comments);

        // 2. Act(執行) : 調用 CommentService 的 getAllComments 方法
        List<Comment> result = commentService.getAllComments();

        // 3. Assert(斷言) : 驗證返回的留言列表不為 null，並且數量與內容正確
        assertNotNull(result, "返回的留言列表不應為 null");
        assertEquals(2, result.size(), "留言數量應為 2");
        assertEquals("Joe", result.get(0).getName(), "第一筆留言的姓名應為 Joe");
        assertEquals("Mike", result.get(1).getName(), "第二筆留言的姓名應為 Alice");

        // 驗證 repository.findAll() 方法呼叫了一次
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void getCommentById() {
    }

    @Test
    void getCommentsByName() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteCommentById() {
    }
}