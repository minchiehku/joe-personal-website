package com.example.personalwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // 允許所有人訪問以下路徑
                        .requestMatchers("/", "/index.html", "/comments.html", "/assets/**", "/csrf").permitAll()
                        // 允許未經認證的用戶對 /api/comments 執行 GET 和 POST 請求
                        .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comments/**").permitAll()
                        // 需要認證的請求
                        .requestMatchers("/admin/**", "/commentManagement.html").authenticated()
                        .requestMatchers("/api/comments/**").authenticated() // 其他對 /api/comments/** 的請求需要認證（如 PUT、DELETE）
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        return http.build();
    }

    // 使用 BCryptPasswordEncoder 作為密碼編碼器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置用戶詳細信息服務
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("password")) // 使用密碼編碼器進行編碼
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    // 登入成功處理
    private SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/commentManagement.html"); // 成功後跳轉到 commentManagement.html
    }

    // 登入失敗處理
    private SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login.html?error=true"); // 登入失敗後跳轉到 login.html 並顯示錯誤
    }
}
