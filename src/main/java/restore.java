//import com.example.personalwebsite.model.Comment;
//import com.example.personalwebsite.repository.CommentRepository;
//import com.example.personalwebsite.service.CommentService;
//import jakarta.persistence.*;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class restore {
//
//    package com.example.personalwebsite.controller;
//
//import com.example.personalwebsite.model.Comment;
//import com.example.personalwebsite.service.CommentService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//    /**
//     * CommentController 是一個 REST 控制器類別，負責處理與留言板相關的 API 請求。
//     * 它使用 Spring Boot 的 @RestController 來暴露 API 端點，並使用 CommentService 來處理具體的業務邏輯。
//     */
//    @RestController
//    @RequestMapping("/api/comments") // 將所有關於留言的 API 請求映射到 /api/comments URL 路徑下
//    public class CommentController {
//
//        private final CommentService commentService;
//
//        /**
//         * 透過構造函數注入 CommentService，該服務負責具體的業務邏輯處理。
//         *
//         * @param commentService 自動注入的 CommentService 對象
//         */
//        public CommentController(CommentService commentService) {
//            this.commentService = commentService;
//        }
//
//        /**
//         * 處理 POST 請求，負責創建新留言並將其保存到資料庫中。
//         * 前端傳來的留言數據會作為 @RequestBody 的參數進行反序列化。
//         *
//         * @param comment 前端傳遞過來的留言數據，包含姓名、電子郵件和留言內容
//         * @return 返回保存到資料庫的留言對象，包含自動生成的 ID 和時間戳
//         */
//        @PostMapping
//        public Comment createComment(@RequestBody Comment comment) {
//            return commentService.saveComment(comment); // 調用服務層來保存新留言
//        }
//
//        /**
//         * 處理 GET 請求，負責從資料庫中檢索所有留言並返回給前端。
//         * 前端可以使用該 API 來顯示所有已提交的留言。
//         *
//         * @return 返回包含所有留言的列表，這些留言由 Comment 對象表示
//         */
//        @GetMapping
//        public List<Comment> getAllComments() {
//            return commentService.getAllComments(); // 調用服務層來檢索所有留言
//        }
//
//
//
//    }
//
//
//    //Comment
//    package com.example.personalwebsite.model;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//    /**
//     * Comment 實體類別，對應資料庫中的 comment_board 表，用於儲存用戶留言的信息。
//     * 它使用 JPA 註解來定義與資料庫表的映射關係。
//     */
//    @Entity
//    @Table(name = "comment_board") // 指定該類別映射到資料庫中的 comment_board 表
//    public class Comment {
//
//        // 主鍵 ID，自動生成
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主鍵策略
//        private Long id;
//
//        // 用戶姓名
//        private String name;
//
//        // 用戶的電子郵件
//        private String email;
//
//        // 用戶的留言內容
//        private String comment;
//
//        // 留言的時間戳，默認為當前時間
//        private LocalDateTime timestamp;
//
//        /**
//         * 無參數建構函數，當創建 Comment 對象時自動設置 timestamp 為當前時間。
//         */
//        public Comment() {
//            this.timestamp = LocalDateTime.now(); // 預設的時間戳為當前時間
//        }
//
//        // Getter 和 Setter 方法，用於操作 Comment 的屬性
//
//        public String getComment() {
//            return comment;
//        }
//
//        public void setComment(String comment) {
//            this.comment = comment;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public LocalDateTime getTimestamp() {
//            return timestamp;
//        }
//
//        public void setTimestamp(LocalDateTime timestamp) {
//            this.timestamp = timestamp;
//        }
//    }
//
//
//    //CommentService
//    package com.example.personalwebsite.service;
//
//import com.example.personalwebsite.model.Comment;
//import com.example.personalwebsite.repository.CommentRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//    /**
//     * CommentService 是一個服務層類別，負責處理與留言（Comment）相關的業務邏輯。
//     * 它與 CommentRepository 互動來執行資料庫操作，如保存和檢索留言。
//     */
//    @Service
//    public class CommentService {
//
//        private final CommentRepository commentRepository;
//
//        /**
//         * 使用構造函數注入方式來注入 CommentRepository。
//         * Spring Boot 會自動注入這個依賴，使得服務層能夠與資料庫進行互動。
//         *
//         * @param commentRepository 注入的 CommentRepository 物件，用來操作資料庫
//         */
//        public CommentService(CommentRepository commentRepository) {
//            this.commentRepository = commentRepository;
//        }
//
//        /**
//         * 保存一個新的留言到資料庫中。
//         *
//         * @param comment 要保存的 Comment 物件，包含用戶提交的留言信息
//         * @return 返回保存後的 Comment 物件，這個物件包含了自動生成的 ID 和時間戳
//         */
//        public com.example.personalwebsite.model.Comment saveComment(com.example.personalwebsite.model.Comment comment) {
//            return commentRepository.save(comment); // 調用 CommentRepository 來保存留言
//        }
//
//        /**
//         * 從資料庫中檢索所有的留言。
//         *
//         * @return 返回包含所有留言的 List<Comment> 列表
//         */
//        public List<com.example.personalwebsite.model.Comment> getAllComments() {
//            return commentRepository.findAll(); // 調用 CommentRepository 來查找所有留言
//        }
//
//
//    }
//
//
//    //CommentRepository
//    package com.example.personalwebsite.repository;
//
//import com.example.personalwebsite.model.Comment;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//    /**
//     * CommentRepository 是一個介面，繼承了 JpaRepository。
//     * 它為 Comment 實體類別提供了對資料庫的基本 CRUD 操作（創建、讀取、更新、刪除）。
//     *
//     * Spring Data JPA 自動生成此介面所需要的具體實現，因此你無需自己撰寫具體的 SQL 查詢。
//     */
//    public interface CommentRepository extends JpaRepository<com.example.personalwebsite.model.Comment, Long> {
//
//        // 這裡可以根據需要擴展自定義查詢方法
//        // JpaRepository 已經提供了基本的 CRUD 方法，比如：
//        // - findAll() : 查詢所有 Comment
//        // - findById(Long id) : 根據 ID 查詢特定的 Comment
//        // - save(Comment comment) : 保存或更新 Comment
//        // - deleteById(Long id) : 根據 ID 刪除 Comment
//    }
//
//
//
//}
