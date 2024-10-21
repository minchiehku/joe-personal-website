package com.example.personalwebsite.controller;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CommentController 是一個 REST 控制器類別，負責處理與留言板相關的 API 請求。
 * 它使用 Spring Boot 的 @RestController 來暴露 API 端點，並使用 CommentService 來處理具體的業務邏輯。
 */
@RestController
@RequestMapping("/api/comments") // 將所有關於留言的 API 請求映射到 /api/comments URL 路徑下
public class CommentController {

    private final CommentService commentService;

    /**
     * 透過構造函數注入 CommentService，該服務負責具體的業務邏輯處理。
     *
     * @param commentService 自動注入的 CommentService 對象
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 處理 POST 請求，負責創建新留言並將其保存到資料庫中。
     * 前端傳來的留言數據會作為 @RequestBody 的參數進行反序列化。
     *
     * @param comment 前端傳遞過來的留言數據，包含姓名、電子郵件和留言內容
     * @return 返回保存到資料庫的留言對象，包含自動生成的 ID 和時間戳
     */
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment); // 調用服務層來保存新留言
    }

    /**
     * 處理 GET 請求，負責從資料庫中檢索所有留言並返回給前端。
     * 前端可以使用該 API 來顯示所有已提交的留言。
     *
     * @return 返回包含所有留言的列表，這些留言由 Comment 對象表示
     */
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments(); // 調用服務層來檢索所有留言
    }

    /**
     * 根據留言 ID 獲取具體的留言。
     *
     * @param id 留言的唯一標識符
     * @return 返回該 ID 對應的 Comment 對象
     */
    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    /**
     * 根據姓名搜尋留言。
     *
     * @param name 使用者名稱
     * @return 返回該名稱對應的留言列表
     */
    @GetMapping("/search")
    public List<Comment> getCommentsByName(@RequestParam String name) {
        return commentService.getCommentsByName(name);
    }

    /**
     * 處理 PUT 請求，用於更新指定 ID 的留言。
     *
     * @param id 留言的唯一標識符
     * @param updatedComment 包含更新後內容的 Comment 對象
     * @return 返回更新後的 Comment 對象
     */
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        return commentService.updateComment(id, updatedComment);
    }

    /**
     * 處理 DELETE 請求，根據 ID 刪除留言。
     *
     * @param id 留言的唯一標識符
     */
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteCommentById(id);
    }
}
