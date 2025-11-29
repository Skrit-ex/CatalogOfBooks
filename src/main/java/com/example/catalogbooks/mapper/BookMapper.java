package com.example.catalogbooks.mapper;

import com.example.catalogbooks.dto.BookDto;
import com.example.catalogbooks.entity.Book;

import java.util.Arrays;

public class BookMapper {

    public Book mapToEntity(BookDto dto) {
        Book book = new Book();
        book.setAuthor(Arrays.toString(dto.getAuthors()));
        book.setDescription(dto.getDescription());
        book.setTitle(dto.getTitle());
        book.setPublisher(dto.getPublisher());
        book.setPublishedDate(dto.getPublishedDate());
        book.setIsbn(dto.getIsbn13() != null ? dto.getIsbn13() : dto.getIsbn10());
        book.setCoverUrl(dto.getThumbnail());
        return book;
    }
}
