package com.korit.springboot_study.dto.request.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqUpdateMajorDto {
    @ApiModelProperty(value = "학과명", example = "컴퓨터공학과", required = true)
    @Pattern(regexp = "^[가-힣]+$", message = "학과명은 한글만으로 이루어져야 하며, 빈 문자열이나 공백, 특수문자 및 영어는 허용되지 않습니다. 이 규칙을 따르지 않으면 유효하지 않은 학과명으로 처리됩니다")
    private String majorName;
}
