package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.Book;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepository {

    @Autowired
    private BookMapper bookMapper;

    public Optional<List<Book>> findBookAll() {
        List<Book> foundBooks = bookMapper.selectBooksAll("");

        return foundBooks.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundBooks);
    }

    public Optional <List<Book>> findBooksByName(String bookName) {
        List<Book> foundBooks = bookMapper.selectBooksAll(bookName);

        return foundBooks.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundBooks);

    }
}
