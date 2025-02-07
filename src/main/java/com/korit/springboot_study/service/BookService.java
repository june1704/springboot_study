package com.korit.springboot_study.service;

import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.request.ReqSearchBookDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.repository.BookRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(ReqAddBookDto reqAddBookDto) {
        return bookRepository
                .save(reqAddBookDto.toBook())
                .get();
    }

    public List<Book> getAllBooks(ReqSearchBookDto reqSearchBookDto) throws Exception {
        return bookRepository.findBookAll(reqSearchBookDto.getKeyword())
                .orElseThrow(() -> new NotFoundException("조회된 도서명이 없습니다."));
    }
}
