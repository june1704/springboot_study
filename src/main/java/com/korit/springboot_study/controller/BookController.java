package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.request.ReqSearchBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "도서 API")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiModelProperty(value = "도서 추가")
    @PostMapping("/api/study/book")
    public ResponseEntity<SuccessResponseDto<Book>> addBook(
            @Valid
            @RequestBody ReqAddBookDto reqAddBookDto) {
        return ResponseEntity.ok().body(
                new SuccessResponseDto<>(bookService.addBook(reqAddBookDto)));
    }

    @ApiOperation(value = "도서 전체 조회")
    @GetMapping("/api/study/Books")
    public ResponseEntity<SuccessResponseDto<List<Book>>> searchBooks(
            @ModelAttribute ReqSearchBookDto reqSearchBookDto) throws Exception {
        return ResponseEntity.ok().body(
                new SuccessResponseDto<>(bookService.getAllBooks(reqSearchBookDto)));
    }
}
