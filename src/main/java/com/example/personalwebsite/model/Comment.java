package com.example.personalwebsite.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Comment 實體類別，對應資料庫中的 comment_board 表，用於儲存用戶留言的信息。
 * 它使用 JPA 註解來定義與資料庫表的映射關係。
 */
@Entity
@Table(name = "comment_board") // 指定該類別映射到資料庫中的 comment_board 表
public class Comment {

    // 主鍵 ID，自動生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主鍵策略
    private Long id;

    // 用戶姓名
    private String name;

    // 用戶的電子郵件
    private String email;

    // 用戶的留言內容
    private String comment;

    // 留言的時間戳，默認為當前時間
    private LocalDateTime timestamp;

    /**
     * 無參數建構函數，當創建 Comment 對象時自動設置 timestamp 為當前時間。
     */
    public Comment() {
        this.timestamp = LocalDateTime.now(); // 預設的時間戳為當前時間
    }

    // Getter 和 Setter 方法，用於操作 Comment 的屬性

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
