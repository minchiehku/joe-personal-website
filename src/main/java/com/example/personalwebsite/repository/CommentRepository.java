package com.example.personalwebsite.repository;

import com.example.personalwebsite.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CommentRepository 是一個介面，繼承了 JpaRepository。
 * Spring Data JPA 會自動掃描並檢測所有繼承自 JpaRepository（或其他 Spring Data Repository 介面）的接口，並自動為它們生成實作。
 * 即使不加 @Repository 註解，Spring 仍然會將該介面納入容器管理。
 * 它為 Comment 實體類別提供了對資料庫的基本 CRUD 操作（創建、讀取、更新、刪除）。
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 根據名稱查詢留言
    List<Comment> findByName(String name);
}
