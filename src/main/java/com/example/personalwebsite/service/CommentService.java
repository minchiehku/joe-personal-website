package com.example.personalwebsite.service;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CommentService 是一個服務層類別，負責處理與留言（Comment）相關的業務邏輯。
 * 它與 CommentRepository 互動來執行資料庫操作，如保存和檢索留言。
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 使用建構函數注入方式來注入 CommentRepository。
     * Spring Boot 會自動注入這個依賴，使得服務層能夠與資料庫進行互動。
     *
     * @param commentRepository 注入的 CommentRepository 物件，用來操作資料庫
     */
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 保存一個新的留言到資料庫中。
     *
     * @param comment 要保存的 Comment 物件，包含用戶提交的留言信息
     * @return 返回保存後的 Comment 物件，這個物件包含了自動生成的 ID 和時間戳
     */
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment); // 調用 CommentRepository 來保存留言
    }

    /**
     * 從資料庫中檢索所有的留言。
     *
     * @return 返回包含所有留言的 List<Comment> 列表
     */
    public List<Comment> getAllComments() {
        return commentRepository.findAll(); // 調用 CommentRepository 來查找所有留言
    }

    /**
     * 根據 ID 獲取留言。
     *
     * @param id 留言的唯一標識符
     * @return 返回對應的 Comment 對象
     */
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    /**
     * 根據名稱查詢留言。
     *
     * @param name 使用者名稱
     * @return 返回符合名稱條件的 Comment 列表
     */
    public List<Comment> getCommentsByName(String name) {
        return commentRepository.findByName(name);
    }

    /**
     * 更新留言。
     *
     * @param id 留言的唯一標識符
     * @param updatedComment 包含更新內容的 Comment 對象
     * @return 返回更新後的 Comment 對象
     */
    public Comment updateComment(Long id, Comment updatedComment) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            comment.setName(updatedComment.getName());
            comment.setEmail(updatedComment.getEmail());
            comment.setComment(updatedComment.getComment());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found with ID: " + id);
        }
    }

    /**
     * 根據 ID 刪除留言。
     *
     * @param id 留言的唯一標識符
     */
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
