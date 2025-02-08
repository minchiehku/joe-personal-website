package com.example.personalwebsite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Comment 實體類別，對應資料庫中的 comment_board 表，用於儲存用戶留言的信息。
 * 它使用 JPA 註解來定義與資料庫表的映射關係。
 */
@Entity //用於標記Java 類別是為 JPA（Java Persistence API）實體類別。實體類別通常用表示資料庫中所對應的表格，每個實體類別的物件對應到資料庫表格中的一筆記錄，變將 Java 對象與資料庫表格之間的資料映射起來。
@Table(name = "comment_board") // 指定該類別映射到資料庫中的 comment_board 表
public class Comment {

    // 主鍵 ID，自動生成
    @Id //用於標示 id 屬性作為主鍵。
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主鍵策略, 當插入新資料時，資料庫會自動生成並遞增該欄位的值。
    @Schema(description = "主鍵 ID, 自動產生", example = "1")
    private Long id;

    @Schema(description = "訪客姓名", example = "Joe")
    // 訪客姓名 可以使用 @Column(name = "資料庫欄位名稱") 來明確指定對應關係
    private String name;

    @Schema(description = "訪客電子郵件", example = "joe@example.com")
    // 訪客的電子郵件
    private String email;

    @Schema(description = "留言內容", example = "這是一則留言")
    // 訪客的留言內容
    private String comment;

    @Schema(description = "留言時間, 自動產生", example = "2025-02-04T16:25:28.939")
    // 留言的時間戳，默認為當前時間
    private LocalDateTime timestamp;

    /**
     * 無參數建構函數，當創建 Comment 對象時自動設置 timestamp 為當前時間。
     */
    public Comment() {
        this.timestamp = LocalDateTime.now(); // 預設的時間戳為當前時間
    }

    public Comment(long l, String joe, String mail, String 這是一則留言) {
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
