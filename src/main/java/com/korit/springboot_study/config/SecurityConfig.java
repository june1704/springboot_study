package com.korit.springboot_study.config;

import com.korit.springboot_study.security.exception.CustomAuthenticationEntryPoint;
import com.korit.springboot_study.security.filter.CustomAuthenticationFilter;
import com.korit.springboot_study.security.filter.JwtAuthenticationFilter;
import com.korit.springboot_study.security.oauth2.OAuth2Service;
import com.korit.springboot_study.security.oauth2.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 설정을 활성화하는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint; // 인증 실패 시 처리하는 클래스

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // JWT 인증을 처리하는 커스텀 필터

    // BCryptPasswordEncoder 빈 등록, 비밀번호 암호화에 사용
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF 비활성화: REST API에서는 CSRF 보호가 필요 없으므로 비활성화
        http.csrf().disable();

        // HTTP Basic 인증 비활성화: JWT 방식으로 인증하므로 HTTP Basic 인증은 사용하지 않음
        http.httpBasic().disable();

        // 폼 로그인 비활성화: JWT 로그인 방식만 사용하므로 폼 로그인을 비활성화
        http.formLogin().disable();

        // 세션 관리: Stateless로 설정하여 세션을 사용하지 않음
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용 안 함

        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 인증 예외 처리: 인증되지 않은 사용자에 대한 처리를 커스텀한 핸들러로 설정
        http.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        http.oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint()
                                .userService(oAuth2Service);


        // 요청에 대한 권한 설정
        http.authorizeRequests()
                .antMatchers(
                        "/swagger-ui/**", // Swagger UI 접근 허용
                        "/v2/api-docs/**", // Swagger API Docs 접근 허용
                        "/v3/api-docs/**", // Swagger API Docs 접근 허용
                        "/swagger-resources/**" // Swagger 리소스 접근 허용
                )
                .permitAll() // 위 경로들은 모두 인증 없이 접근 가능
                .antMatchers(HttpMethod.GET) // 인증 관련 API도 인증 없이 접근 허용
                .permitAll()
                .anyRequest() // 나머지 모든 요청
                .authenticated(); // 인증된 사용자만 접근 가능
//                .and()
//                .exceptionHandling() // 인증 실패 시 처리할 예외 핸들러 설정
//                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }
}

/*
흐름 설명
설정 클래스 활성화:

@Configuration과 @EnableWebSecurity 어노테이션을 통해 스프링 시큐리티 설정을 활성화하고, SecurityConfig 클래스를 보안 설정 클래스로 지정합니다.
BCryptPasswordEncoder:

@Bean으로 등록된 BCryptPasswordEncoder는 비밀번호를 암호화하는데 사용됩니다.
Spring Security의 기본 비밀번호 암호화 방식인 BCrypt를 사용하여, 저장된 비밀번호를 안전하게 보호합니다.

HTTP 보안 설정:

http.csrf().disable()과 같은 설정으로 CSRF 보호를 비활성화합니다. REST API에서는 CSRF 공격이 발생하지 않으므로 이를 비활성화합니다.
http.httpBasic().disable()과 http.formLogin().disable()은 기본 인증 및 폼 로그인을 비활성화하고, JWT 기반의 인증만 사용하도록 합니다.
http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)로 세션을 사용하지 않도록 설정합니다.
JWT 토큰이 인증 정보를 담고 있기 때문에, 세션을 사용하지 않고 상태 없는(stateless) 인증 방식을 적용합니다.

JWT 인증 필터 추가:

http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)는
JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter보다 앞에 배치하여,
요청에서 JWT 토큰을 먼저 확인하고 인증을 처리할 수 있도록 합니다.

인증 실패 처리:

http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)로 인증 실패 시
CustomAuthenticationEntryPoint를 호출합니다. 이 객체는 인증이 실패한 요청에 대해 어떻게 처리할지를 정의합니다(예: 401 Unauthorized 오류 반환).

권한 설정:

http.authorizeRequests()를 사용하여 요청 경로에 따른 접근 제어를 설정합니다. 예를 들어:
/swagger-ui/**, /api/auth/**와 같은 경로는 인증 없이 접근할 수 있도록 permitAll()을 사용합니다.
그 외의 모든 요청은 인증된 사용자만 접근할 수 있도록 authenticated()로 설정합니다.


전체 흐름:

클라이언트가 요청을 보낼 때, 이 요청은 먼저 JwtAuthenticationFilter를 통해 JWT 토큰을 검사합니다.
인증되지 않은 사용자가 요청을 보내면, CustomAuthenticationEntryPoint가 호출되어 인증 오류를 처리합니다.
인증이 성공한 경우, 요청은 지정된 경로별 접근 권한에 따라 허용되거나 거부됩니다.
이 설정을 통해, 스프링 시큐리티는 인증과 인가를 처리하며, JWT를 기반으로 한 상태 없는 인증을 제공합니다.

*/