package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/study/Books")
    @ApiOperation(value = "책 전체 조회")
    public ResponseEntity<SuccessResponseDto<List<Book>>> getBook(String bookName) throws NotFoundException {
        return ResponseEntity.ok().body(bookService.getBooksAll());
    }

    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String bookName) {
        List<Book> book = bookService.searchBooks(bookName);
        return ResponseEntity.ok(book);
    }
}
