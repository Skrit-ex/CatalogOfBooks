package com.example.catalogbooks.controller;

import com.example.catalogbooks.dto.BookDto;
import com.example.catalogbooks.entity.Book;
import com.example.catalogbooks.service.BooksGoogleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BooksGoogleService booksGoogleService;

    @GetMapping("/searchBook")
    public String searchBook(@RequestParam String query,
                             @RequestParam(defaultValue = "general") String type,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "title") String sort,
                             @RequestParam(defaultValue = "asc") String direction,
                             Model model) {
        Sort sortOrder = direction.equalsIgnoreCase("asc")
                ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        List<BookDto> books;
        switch (type) {
            case "title" -> books = booksGoogleService.searchByTitle(query);
            case "author" -> books = booksGoogleService.searchByAuthor(query);
            default -> books = booksGoogleService.searchBooks(query);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        Page<BookDto> bookPage = new PageImpl<>(books.subList(start, end), pageable, books.size());

        model.addAttribute("currentPage", bookPage.getNumber());
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("totalItems", bookPage.getTotalElements());
        model.addAttribute("sortFields", sort);
        model.addAttribute("sortDir", direction);
        model.addAttribute("type", type);
        model.addAttribute("query", query);

        return "getBookInformation";
    }
}
