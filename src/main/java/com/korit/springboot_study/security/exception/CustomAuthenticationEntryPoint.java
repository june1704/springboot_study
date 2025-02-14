package com.korit.springboot_study.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component // 스프링이 관리하는 Bean으로 등록
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper; // JSON 형식으로 응답을 작성하기 위한 ObjectMapper 주입

    /*
     * 인증 실패 시 호출되는 메서드
     * @param request 인증이 실패한 요청 객체
     * @param response 클라이언트에 반환할 응답 객체
     * @param authException 인증 예외 정보
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("인증 예외 발생"); // 서버 로그에 인증 실패 메시지 출력
        authException.printStackTrace(); // 예외 정보 상세 출력 (디버깅용)

        // 클라이언트에게 403(Forbidden) 상태 코드 반환
        response.setStatus(403);

        // 응답의 Content-Type을 JSON으로 설정
        response.setContentType("application/json");

        // 응답 인코딩을 UTF-8로 설정 (한글 깨짐 방지)
        response.setCharacterEncoding("UTF-8");

        // JSON 형식으로 인증 실패 메시지 반환
        response.getWriter().println(
                objectMapper.writeValueAsString(Map.of("error", "인증필요합니다!!!!"))
        );
    }
}
/*
클라이언트가 인증이 필요한 리소스에 접근 시도.
유효하지 않은 인증 정보(또는 토큰 누락)로 접근 시, CustomAuthenticationEntryPoint의 commence() 메서드 실행.
로그에 "인증 예외 발생" 출력 및 예외 스택 트레이스를 확인.
클라이언트에 403 Forbidden 상태 코드와 함께 JSON 형식으로 인증 실패 메시지를 반환.

 */
