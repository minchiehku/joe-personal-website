package com.example.personalwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // 允許所有人訪問靜態資源
                        .requestMatchers("/", "/index.html", "/comments.html", "/assets/**", "/csrf").permitAll()
                        // 允許 Swagger 相關路徑（API 文件）
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**"
                        ).permitAll()
                        // ✅ 允許所有人讀取/新增留言
                        .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comments/**").permitAll()
                        // ✅ 讓 PUT、DELETE 需要登入，但支援 Basic Auth，Swagger 也能測試
                        .requestMatchers(HttpMethod.PUT, "/api/comments/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/**").authenticated()
                        .requestMatchers("/admin/**", "/commentManagement.html").authenticated()
                        .anyRequest().authenticated()
                )
                // ✅ 啟用 Basic Auth，讓 Swagger UI 也能登入
                .httpBasic(Customizer.withDefaults())
                // ✅ 啟用表單登入
                .formLogin((form) -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                // ✅ 針對 API 的 CSRF 設定
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/api/comments/**") // ✅ 讓 API 請求無需 CSRF Token
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        return http.build();
    }

    // 使用 BCryptPasswordEncoder 作為密碼編碼器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 設定內建測試帳號
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password")) // 使用密碼編碼
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    // 登入成功處理
    private SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/commentManagement.html"); // 登入成功跳轉
    }

    // 登入失敗處理
    private SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login.html?error=true"); // 登入失敗顯示錯誤
    }
}
