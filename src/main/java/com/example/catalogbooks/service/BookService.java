package com.example.catalogbooks.service;

import com.example.catalogbooks.entity.Book;
import com.example.catalogbooks.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/user")
public class BookService {

    private final BookRepository bookRepository;

    public void saveBook(Book book) {
        bookRepository.save(book);
    }
    public List<Book> findByAuthor(String author) {
        List<Book> listOfBooks = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (listOfBooks.isEmpty()) {
            log.warn("Author {} not found", author);
        } else {
            log.info("Author {} was founded", author);
        }
        return listOfBooks;
    }
}
