package com.example.personalwebsite.controller;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CommentController 是一個 REST 控制器類別，負責處理與留言板相關的 API 請求。
 * 它使用 Spring Boot 的 @RestController 來暴露 API 端點，並使用 CommentService 來處理具體的業務邏輯。
 */
@RestController
@RequestMapping("/api/comments") // 將所有關於留言的 API 請求映射到 /api/comments URL 路徑下
@Tag(name = "Comment API", description = "留言相關 API，包含留言的新增、查詢、更新與刪除")
public class CommentController {

    private final CommentService commentService;

    /**
     * 透過構造函數注入 CommentService，該服務負責具體的業務邏輯處理。
     * 只有一個依賴, 自動注入, 不需加上 @Autowired
     * @param commentService 自動注入的 CommentService 對象
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 處理 POST 請求，負責創建新留言並將其保存到資料庫中。前端傳來的留言數據會作為 @RequestBody 的參數進行反序列化。將請求中的 JSON 字串轉換成對應的 Java 物件
     *
     * @param comment 前端傳遞過來的留言數據，包含姓名、電子郵件和留言內容
     * @return 返回保存到資料庫的留言對象，包含自動生成的 ID 和時間戳
     */
    @Operation(summary = "新增新留言", description = "接收前端傳入留言, 保存到DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功新增留言",
                content = @Content(schema = @Schema(implementation = Comment.class))),
    })
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment); // 調用服務層來保存新留言
    }

    /**
     * 處理 GET 請求，負責從資料庫中檢索所有留言並返回給前端。
     *
     * @return 返回包含所有留言的列表，這些留言由 Comment 對象表示
     */

    @Operation(summary = "取得所有留言", description = "從DB中查詢所有留言")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功返回所有留言",
                    content = @Content(schema = @Schema(implementation = Comment.class)))
    })
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments(); // 調用服務層來檢索所有留言
    }

    /**
     * 根據留言 ID 獲取具體的留言。@PathVariable 取得URL路徑
     *
     * @param id 留言的唯一標識符
     * @return 返回該 ID 對應的 Comment 對象
     */

    @Operation(summary = "根據ID查詢留言", description = "根據留言的唯一識別ID查詢留言")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功回傳留言",
                    content = @Content(schema = @Schema(implementation = Comment.class)))
    })
    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(
            @Parameter(description = "留言的唯一識別ID", required = true, example = "1")
            @PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    /**
     * 根據姓名搜尋留言。@RequestParam 取得URL參數
     *
     * @param name 使用者名稱
     * @return 返回該名稱對應的留言列表
     */

    @Operation(summary = "根據name查詢留言", description = "根據name查詢留言並回傳對應的留言列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功回傳留言列表",
                content = @Content(schema = @Schema(implementation = Comment.class)))
    })
    @GetMapping("/search")
    public List<Comment> getCommentsByName(
            @Parameter(description = "查詢的name", required = true, example = "Joe")
            @RequestParam String name) {
        return commentService.getCommentsByName(name);
    }

    /**
     * 處理 PUT 請求，用於更新指定 ID 的留言。
     *
     * @param id 留言的唯一標識符
     * @param updatedComment 包含更新後內容的 Comment 對象
     * @return 返回更新後的 Comment 對象
     */
    @Operation(summary = "根據ID更新留言", description = "根據指定ID更新留言內容")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新留言",
                    content = @Content(schema = @Schema(implementation = Comment.class))),
    })
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id,
                                 @RequestBody Comment updatedComment) {
        return commentService.updateComment(id, updatedComment);
    }

    /**
     * 處理 DELETE 請求，根據 ID 刪除留言。
     *
     * @param id 留言的唯一標識符
     */
    @Operation(summary = "根據ID刪除留言", description = "根據指定ID刪除留言")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功刪除留言",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public void deleteComment(
            @Parameter(description = "留言的指定ID", required = true, example = "1")
            @PathVariable Long id) {
        commentService.deleteCommentById(id);
    }
}
