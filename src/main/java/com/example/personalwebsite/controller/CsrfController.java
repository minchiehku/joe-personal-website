package com.example.personalwebsite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;

/**
 * CSRF 控制器，用於獲取 CSRF Token，防止跨站請求偽造攻擊。
 */
@RestController
@Tag(name = "CSRF API", description = "提供 CSRF 相關的API，確保安全的請求")
public class CsrfController {

    @Operation(summary = "獲取 CSRF Token", description = "前端需使用此API獲取CSRF Token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功獲取 CSRF Token")
    })
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

}
