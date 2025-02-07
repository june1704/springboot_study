package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqAddCategoryDto;
import com.korit.springboot_study.dto.request.ReqSearchCategoryDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Category;
import com.korit.springboot_study.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "카테고리 API")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiModelProperty(value = "카테고리 추가")
    @PostMapping("/api/study/category")
    public ResponseEntity<SuccessResponseDto<Category>> addCategory(
            @Valid
            @RequestBody ReqAddCategoryDto reqAddCategoryDto) {
        return ResponseEntity.ok().body(
                new SuccessResponseDto<>(categoryService.addCategory(reqAddCategoryDto)));
    }

    @ApiOperation(value = "카테고리 전체 조회")
    @GetMapping("/api/study/Categorys")
    public ResponseEntity<SuccessResponseDto<List<Category>>> searchCategory(
            @ModelAttribute ReqSearchCategoryDto reqSearchCategoryDto) throws Exception {
        return ResponseEntity.ok().body(
                new SuccessResponseDto<>(categoryService.getAllCategorys(reqSearchCategoryDto)));
    }
}
