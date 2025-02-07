package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqAddAuthorDto;
import com.korit.springboot_study.dto.request.ReqSearchAuthorDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Author;
import com.korit.springboot_study.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "저자 API")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @ApiModelProperty(value = "저자 추가")
    @PostMapping("/api/study/author")
    public ResponseEntity<SuccessResponseDto<Author>> addAuthor(
            @Valid
            @RequestBody ReqAddAuthorDto reqAddAuthorDto) {
        return ResponseEntity.ok().body(
                new SuccessResponseDto<>(authorService.addAuthor(reqAddAuthorDto)));
    }
    @ApiOperation(value = "저자 전체 조회")
    @GetMapping("/api/study/Authors")
    public ResponseEntity<SuccessResponseDto<List<Author>>> searchAuthors(
            @ModelAttribute ReqSearchAuthorDto reqSearchAuthorDto) throws Exception {
        return ResponseEntity.ok().body(
                new SuccessResponseDto<>(authorService.getAllAuthors(reqSearchAuthorDto)));
    }
}
