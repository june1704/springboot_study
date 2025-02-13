package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqSigninDto;
import com.korit.springboot_study.dto.request.ReqSignupDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.User;
import com.korit.springboot_study.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "계정 API") // Swagger 문서에 "계정 API"로 표시
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService; // 인증 관련 비즈니스 로직을 처리하는 서비스 클래스 주입

    /*
     * 회원가입 요청 처리 메서드
     * @param reqSignupDto 회원가입 요청 DTO, @Valid를 통해 요청 데이터 검증
     * @return User 객체를 감싼 SuccessResponseDto 반환
     */
    @PostMapping("/api/auth/signup")
    @ApiOperation(value = "회원가입") // Swagger 문서에 "회원가입"으로 표시
    public ResponseEntity<SuccessResponseDto<User>> signup(@Valid @RequestBody ReqSignupDto reqSignupDto) throws MethodArgumentNotValidException {
        // 회원가입 요청 처리 후, 성공 응답을 반환
        return ResponseEntity.ok().body(new SuccessResponseDto<>(authenticationService.signup(reqSignupDto)));
    }

    /*
     * 로그인 요청 처리 메서드
     * @param reqSigninDto 로그인 요청 DTO, @Valid를 통해 요청 데이터 검증
     * @return JWT 토큰 문자열을 감싼 SuccessResponseDto 반환
     */
    @PostMapping("/api/auth/signin")
    @ApiOperation(value = "로그인") // Swagger 문서에 "로그인"으로 표시
    public ResponseEntity<SuccessResponseDto<String>> signin(@Valid @RequestBody ReqSigninDto reqSigninDto) throws MethodArgumentNotValidException {
        // 로그인 요청 처리 후, JWT 토큰을 반환
        return ResponseEntity.ok().body(new SuccessResponseDto<>(authenticationService.signin(reqSigninDto)));
    }
}


/*

📖 클래스 흐름 설명
1. 클라이언트 요청 진입
클라이언트가 /api/auth/signup 또는 /api/auth/signin 엔드포인트로 POST 요청을 보냅니다.
회원가입: ReqSignupDto 객체에 사용자 정보(아이디, 비밀번호, 이름 등)를 담아 요청.
로그인: ReqSigninDto 객체에 아이디와 비밀번호를 담아 요청.

2. 요청 유효성 검증 (@Valid)
@Valid 어노테이션이 붙은 파라미터는 요청 시 javax.validation 규칙에 따라 검증됩니다.
예: 아이디나 비밀번호가 null이거나 길이가 규칙에 맞지 않으면 MethodArgumentNotValidException 발생.

3. 비즈니스 로직 처리 (AuthenticationService)
authenticationService.signup(reqSignupDto)
회원가입 요청을 받아 DB에 사용자 정보를 저장.
비밀번호는 BCryptPasswordEncoder를 사용하여 암호화.
authenticationService.signin(reqSigninDto)
로그인 요청 시, 전달받은 아이디와 비밀번호를 확인.
검증 성공 시, JWT 토큰을 발급하여 반환.

4. 응답 반환
모든 응답은 SuccessResponseDto 객체로 감싸서 반환.
회원가입 성공 시: 가입된 User 객체 반환.
로그인 성공 시: 생성된 JWT 토큰 문자열 반환.

5. Swagger 문서 설정 (@Api, @ApiOperation)
클래스 레벨: @Api(tags = "계정 API") → Swagger UI에 "계정 API"로 표시.
메서드 레벨: @ApiOperation(value = "회원가입"), @ApiOperation(value = "로그인")으로 설명 제공.

🔍 요청 흐름 요약
클라이언트가 /api/auth/signup 또는 /api/auth/signin으로 POST 요청 전송.
@Valid 검증 통과 시, AuthenticationService의 signup() 또는 signin() 메서드 호출.
signup()은 사용자 등록 후 User 객체 반환, signin()은 로그인 성공 시 JWT 토큰 반환.
결과는 SuccessResponseDto로 감싸서 HTTP 200 상태 코드와 함께 응답.
이 구조를 통해 클라이언트는 회원가입과 로그인을 요청하고, 서버는 JWT 토큰을 발급하여 인증 흐름을 유지합니다. ✅

*/