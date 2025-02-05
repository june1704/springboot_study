package com.korit.springboot_study.dto.response.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "잘못된 요청 응답 DTO")
public class BadRequestResponseDto<T> extends ResponseDto<T> {
    @ApiModelProperty(value = "HTTP 상태 코드", example = "400")
    private final int status;
    @ApiModelProperty(value = "응답 메세지", example = "잘못된 요청입니다.")
    private String message;

    public BadRequestResponseDto(T data) {
        super(data);
        status = 400;
        message = "잘못된 요청입니다.";
    }
}
/*
✔ 부모 클래스의 생성자 호출 후 자식 클래스의 필드를 초기화하기 위해
✔ 추후 status를 다른 값으로 설정할 확장성을 고려하여
✔ API 문서 자동화 도구(Swagger)와의 일관성을 유지하기 위해

✅ 따라서 private final int status = 400; 대신 생성자에서 status = 400;를 할당하는 것입니다.
 */