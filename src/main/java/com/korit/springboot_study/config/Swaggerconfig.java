package com.korit.springboot_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swaggerconfig {

    // Swagger 설정을 위한 Docket 객체를 생성하는 메서드
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2) // Swagger 2.0을 사용하여 문서화
                .select() // API 선택을 위한 설정 시작
                .apis(RequestHandlerSelectors.basePackage("com.korit.springboot_study.controller")) // 해당 패키지 안에 있는 모든 컨트롤러 스웨거 적용
                .paths(PathSelectors.any()) // 모든 URL 스웨거 적용
                .build() // 설정을 적용하여 Docket 객체 생성
                .apiInfo(getApiInfo()); // API 정보 설정 적용
    }

    // Swagger 문서의 제목, 설명, 버전, 담당자 정보를 설정하는 메서드
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("API 문서 제목")
                .description("API 문서 설명")
                .version("1.0") // API 버전 정보
                .contact(new Contact("관리자", "주소", "이메일")) // API 담당자의 이름, 홈페이지 URL, 이메일 정보 설정
                .build(); // 설정을 적용하여 ApiInfo 객체 생성
    }
}
