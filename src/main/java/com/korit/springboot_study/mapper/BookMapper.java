package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> selectBooksAll(@Param("bookName")String bookName);
}
