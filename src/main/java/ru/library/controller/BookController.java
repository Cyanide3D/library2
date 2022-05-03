package ru.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.library.dto.BookDto;
import ru.library.model.Book;
import ru.library.model.Genre;
import ru.library.service.AuthorService;
import ru.library.service.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Book> getBooks() {
        return service.getAllNonRentedBooks();
    }
    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody BookDto book) {
        return ResponseEntity.ok(service.save(book, authorService.getAuthorById(book.getAuthor())));
    }

    @GetMapping("/genres")
    public List<String> genres() {
        return Arrays.stream(Genre.values()).map(Genre::name).collect(Collectors.toList());
    }

    @GetMapping("/author/{id}")
    public List<Book> getBooksByAuthor(@PathVariable("id") Long id) {
        return service.getAllByAuthor(authorService.getAuthorById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
