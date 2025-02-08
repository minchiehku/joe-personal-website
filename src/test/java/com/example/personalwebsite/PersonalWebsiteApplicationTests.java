package com.example.personalwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // 指定使用測試配置檔案
class PersonalWebsiteApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Spring Boot 成功啟動！");
    }
}
