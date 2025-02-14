package com.example.personalwebsite.controller;

import com.example.personalwebsite.model.Comment;
import com.example.personalwebsite.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CommentControllerTest 測試類別
 *
 * 主要用於測試 CommentController 的各個 API 行為，這裡只載入 Controller 層，
 * 其他相依（例如 CommentService）則以 MockBean 的方式模擬，以達到單元測試的隔離。
 */
@WebMvcTest(CommentController.class) // 只載入 CommentController 及其相關組件，不包含 Service 與 Repository
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)  // 讓 JUnit 5 支援 Mockito 功能
class CommentControllerTest {

    // Logger 用來輸出測試過程中需要的日誌訊息
    private static final Logger logger = LoggerFactory.getLogger(CommentControllerTest.class);

    // Autowired 注入 MockMvc，由 Spring Boot Test 自動配置, 透過MockMvc 模擬HTTP請求與回應, 進而驗證狀態馬與JSON結構
    @Autowired
    private MockMvc mockMvc;

    // 使用 @MockBean 將 CommentService 模擬出來，並注入到 CommentController 中
    @MockBean
    private CommentService commentService;

    /**
     * 測試 GET /api/comments API
     * 此測試案例驗證當請求 /api/comments 時，Controller 是否正確呼叫 CommentService 並返回預期的 JSON 結構與資料。
     *
     * @throws Exception 測試執行期間可能拋出的例外
     */
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})  // 模擬一個已登入的使用者，以便通過 Spring Security 驗證
    void testGetAllComments() throws Exception {
        logger.info("開始測試 getAllComments()");

        // 1. Arrange (安排) : 準備測試資料：建立兩個 Comment 物件，代表兩個模擬的留言
        Comment comment1 = new Comment(1L, "Bob", "bob@gmail.com", "模擬留言1");
        Comment comment2 = new Comment(2L, "Merry", "merry@gmail.com", "模擬留言2");
        List<Comment> mockComments = Arrays.asList(comment1, comment2);

        // 模擬 Service 層行為：當 commentService.getAllComments() 方法被呼叫時，返回我們預先準備的 mockComments
        when(commentService.getAllComments()).thenReturn(mockComments);

        // 2. Act(執行) : 使用 MockMvc 發送 GET 請求到 /api/comments 並驗證回應：
        mockMvc.perform(get("/api/comments"))
                // 3. Assert(斷言) : 驗證回傳的狀態與 JSON 資料是否符合預期
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Merry"));

        logger.info("測試成功: getAllComments() 返回預期的結果");

        // 驗證 commentService.getAllComments() 方法在測試期間被呼叫一次
        verify(commentService, times(1)).getAllComments();
    }

    /**
     * 測試 POST /api/comments API：createComment 方法
     *
     * 此測試案例會：
     * 1. 前端請求 Request(Input) 準備一個 JSON 請求內容，包含 name、email、comment 欄位。
     * 2. 模擬 CommentService.saveComment() 方法返回一個帶有自動產生 ID 的 Comment 物件。
     * 3. 使用 MockMvc 發送 POST 請求到 /api/comments，並驗證 HTTP 狀態碼與 JSON 回應內容。
     * 4. 驗證 CommentService.saveComment() 方法是否正確被呼叫。
     *
     * @throws Exception 測試執行期間可能拋出的例外
     */

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // 模擬一個已登入的使用者，以便通過 Security 驗證
    void testCreateComment() throws Exception {
        logger.info("開始測試 createComment()");

        // 1. Arrange(安排): 準備請求的留言資料（JSON 字串），這裡 JSON 字串中不需要包含 id (由 Controller 設定為 null)
        String newCommentJson = "{ \"name\": \"Joe\", \"email\": \"joe@example.com\", \"comment\": \"這是一則留言\" }";

        // 模擬 Service 層的行為, 當呼叫 commentService.saveComment(...) 時，返回一個模擬的 Comment 物件，
        // 在實際運行中，id 是設定由資料庫自動生成的，但在單元測試中，不會連接真實的資料庫，所以模擬 Service 返回的物件，並自行設定一個 id（例如 1L）。
        // timestamp 由 comment類 建構函數時自動產生
        Comment savedComment = new Comment(1L, "Joe", "joe@example.com", "這是一則留言");
        when(commentService.saveComment(any(Comment.class))).thenReturn(savedComment);

        // 2. Act(執行): 使用 MockMvc 發送 POST 請求到 /api/comments
        mockMvc.perform(post("/api/comments")
                        .with(csrf())  //模擬帶有 CSRF Token 的請求
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCommentJson))
                // 3. Assert(斷言)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Joe"))
                .andExpect(jsonPath("$.email").value("joe@example.com"))
                .andExpect(jsonPath("$.comment").value("這是一則留言"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        logger.info("測試成功: createComment() 返回預期的結果");

        // 驗證 CommentService.saveComment() 方法是否被呼叫一次
        verify(commentService, times(1)).saveComment(any(Comment.class));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testCreateComment_MissingField() throws Exception {
        logger.info("開始測試 createComment() 缺少欄位的情況");

        // 準備 JSON 請求資料，但缺少 email 欄位
        String invalidCommentJson = "{ \"name\": \"Joe\", \"comment\": \"這是一則留言\" }";

        // 期望這個請求會返回 HTTP 400 (Bad Request)
        mockMvc.perform(post("/api/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCommentJson))
                .andExpect(status().isBadRequest());

        logger.info("測試成功: 當請求缺少必要欄位時返回 Bad Request");
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testCreateComment_EmptyContent() throws Exception {
        logger.info("開始測試 createComment() 空內容的情況");

        // 準備一個空的 JSON 字串
        String emptyJson = "{}";

        mockMvc.perform(post("/api/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isBadRequest());

        logger.info("測試成功: 空內容請求返回 Bad Request");
    }

    /**
     * 測試 POST /api/comments API：當 Service 拋出異常時返回 500 Internal Server Error
     */
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testCreateComment_ServiceException() throws Exception {
        logger.info("開始測試 createComment() 異常情況");

        String newCommentJson = "{ \"name\": \"Joe\", \"email\": \"joe@example.com\", \"comment\": \"這是一則留言\" }";

        // 模擬 Service 在 saveComment 時拋出 RuntimeException
        when(commentService.saveComment(any(Comment.class)))
                .thenThrow(new RuntimeException("資料庫錯誤"));

        // 發送 POST 請求，預期返回 HTTP 500 與錯誤訊息 "資料庫錯誤"
        mockMvc.perform(post("/api/comments")
                        .with(csrf())  // 確保請求包含 CSRF Token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCommentJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("資料庫錯誤"));

        logger.info("測試成功: 異常情況下返回 Internal Server Error 並顯示錯誤訊息");

        verify(commentService, times(1)).saveComment(any(Comment.class));
    }

}
