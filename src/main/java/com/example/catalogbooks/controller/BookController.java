package com.example.catalogbooks.controller;

import com.example.catalogbooks.service.BooksGoogleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BooksGoogleService booksGoogleService;

    @GetMapping("/searchBook")
    public String searchBook(@RequestParam String query, Model model) {
        model.addAttribute("books", booksGoogleService.searchBooks(query));
        return "searchBook";
    }
}
