package com.example.personalwebsite.repository;

import com.example.personalwebsite.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CommentRepository 是一個介面，繼承了 JpaRepository。
 * 它為 Comment 實體類別提供了對資料庫的基本 CRUD 操作（創建、讀取、更新、刪除）。
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 根據名稱查詢留言
    List<Comment> findByName(String name);
}
