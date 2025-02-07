package com.korit.springboot_study.service;

import com.korit.springboot_study.dto.request.ReqAddAuthorDto;
import com.korit.springboot_study.dto.request.ReqAddBookDto;
import com.korit.springboot_study.dto.request.ReqSearchAuthorDto;
import com.korit.springboot_study.entity.Author;
import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.repository.AuthorRepository;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author addAuthor(ReqAddAuthorDto reqAddAuthorDto) {
        return authorRepository
                .save(reqAddAuthorDto.toAuthor())
                .get();
    }

    public List<Author> getAllAuthors(ReqSearchAuthorDto reqSearchAuthorDto) throws Exception {
        return authorRepository.findAuthorAll(reqSearchAuthorDto.getKeyword())
                .orElseThrow(() -> new NotFoundException("조회된 저자명이 없습니다."));
    }
}
