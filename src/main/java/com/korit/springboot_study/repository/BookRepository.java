package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    @Autowired
    private BookMapper bookMapper;

    public Optional<Book> save(Book book) {
        try {
            bookMapper.insert(book);
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException("이미 존재하는 도서명입니다.");
        }
        return Optional.of(book);
    }

    public Optional<List<Book>> findBookAll(String bookName) {
        System.out.println(bookName);
        return bookMapper.selectBooksAll(bookName).isEmpty()
                ? Optional.empty()
                : Optional.of(bookMapper.selectBooksAll(bookName));
    }
}
