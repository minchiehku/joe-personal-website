package com.example.personalwebsite.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Comment 實體類別，對應資料庫中的 comment_board 表，
 * 用於儲存用戶留言的信息。
 */
@Entity
@Table(name = "comment_board")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主鍵 ID, 自動產生", example = "1")
    private Long id;

    @Schema(description = "訪客姓名", example = "Joe")
    private String name;

    @Schema(description = "訪客電子郵件", example = "joe@example.com")
    private String email;

    @Schema(description = "留言內容", example = "這是一則留言")
    private String comment;

    @Schema(description = "留言時間, 自動產生", example = "2025-02-04T16:25:28.939")
    private LocalDateTime timestamp;

    /**
     * 有參數建構函數，建立 Comment 物件並設置留言時間為當前時間。
     */
    public Comment(Long id, String name, String email, String comment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 無參數建構函數，預設留言時間為當前時間。
     */
    public Comment() {
        this.timestamp = LocalDateTime.now();
    }

    // Getter 與 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
