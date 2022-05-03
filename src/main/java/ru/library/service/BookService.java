package ru.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.library.dto.BookDto;
import ru.library.model.Author;
import ru.library.model.Book;
import ru.library.model.Genre;
import ru.library.repo.BookRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public void update(Book book) {
        bookRepo.save(book);
    }

    public void delete(Long id) {
        final Book book = getBookById(id);
        bookRepo.delete(book);
    }

    public Book save(BookDto dto, Author author) {
        Book book = new Book();
        book.setImg(dto.getImg());
        book.setDescription(dto.getDescription());
        book.setName(dto.getName());
        book.setAuthor(author);
        book.setGenre(Genre.valueOf(dto.getGenre()));
        book.setRented(false);
        book.setDate(LocalDate.now());
        return bookRepo.save(book);
    }

    public void deleteAllByAuthor(Author author) {
        bookRepo.deleteAllByAuthor(author);
    }

    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public List<Book> getAllNonRentedBooks() {
        return bookRepo.findAllByRented(false);
    }

    public List<Book> getAllByAuthor(Author author) {
        return bookRepo.findAllByAuthor(author);
    }

}
