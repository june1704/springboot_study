package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter

//@AllArgsConstructor // required 걸림. 전체 생성자
//@RequiredArgsConstructor // 필수 인 것만 받겠다. final을 써준다. 필수 생성자
@ApiModel(value = "학생정보 조회 학습 DTO")
public class ReqStudentDto {
    @NonNull
    @ApiModelProperty(value = "학생 이름", example = "최명준", required = true)
    private String name;
    @ApiModelProperty(value = "학생 나이", example = "32",  required = false)
    private int age;
}
