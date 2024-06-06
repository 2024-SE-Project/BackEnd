package hgu.se.raonz.commons.security;


import hgu.se.raonz.commons.jwt.JWTAuthenticationFilter;
import hgu.se.raonz.commons.jwt.JWTProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JWTProvider jwtProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests()
                .requestMatchers("/comment/**").permitAll()
                .requestMatchers("/like/**").permitAll()
                // /admin으로 시작하는 요청은 ADMIN 권한이 있는 유저에게만 허용
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // /user 로 시작하는 요청은 USER 권한이 있는 유저에게만 허용
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/post/add").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/post/delete/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/post/update/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/post/get/**").permitAll() //.hasRole("MANAGER")

                .requestMatchers("/material/add").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/material/delete/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/material/update/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/material/get/**").permitAll() //.hasRole("MANAGER")

                .requestMatchers("/faq/add").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/faq/delete/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/faq/update/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/faq/get/**").permitAll() //.hasRole("MANAGER")

                .requestMatchers("/match/add").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/match/delete/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/match/update/**").permitAll() //.hasRole("MANAGER")
                .requestMatchers("/match/get/**").permitAll() //.hasRole("MANAGER")

                .requestMatchers("/rank/team/**").permitAll() //.hasRole("MANAGER")

                .requestMatchers("/login/oauth2/**").permitAll()
                .requestMatchers("/api/v1/oauth2/google").permitAll()
                .requestMatchers("/scrape/**").permitAll()
                .requestMatchers("/mypage/**").permitAll()
                .requestMatchers("/mypage/update/**").permitAll()
                .requestMatchers("/team/add/**").permitAll()
                .requestMatchers("/team/update/**").permitAll()
                .requestMatchers("/team/delete/**").permitAll()
                .requestMatchers("/team/get/**").permitAll()
                .requestMatchers("/team/get/all/**").permitAll()
                .requestMatchers("/my/team/get/**").permitAll()
                .requestMatchers("/team-user/add/**").permitAll()
                .requestMatchers("/team-user/delete/**").permitAll()
                .anyRequest().denyAll().and()
                .oauth2Login(oauth2 -> oauth2
                        .loginProcessingUrl("api/v1/oauth2/*")
                        .loginProcessingUrl("login/oauth2/google/*")
                )
                .addFilterBefore(new JWTAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                            // 권한 문제가 발생했을 때 이 부분을 호출한다.

                            response.setStatus(403);
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=UTF-8");
                            response.getWriter().write("권한이 없는 사용자입니다.");
                        })
                );


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();


        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
