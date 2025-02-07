package com.korit.springboot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private int bookId;
    private String bookName;
    private String authorId;
    private String isbn;
    private String categoryId;
    private String publisherId;
    private String bookimgurl;

    private Author author;
    private Category category;
    private Publisher publisher;
}
