package com.example.catalogbooks.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private String title;
    private String[] authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private String isbn10;
    private String isbn13;
    private String thumbnail;
}
