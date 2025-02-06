package com.korit.springboot_study.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqAddBookDto {
    @ApiModelProperty(value = "책이름", required = true)
    private String bookName;
}
