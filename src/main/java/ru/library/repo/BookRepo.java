package ru.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.Author;
import ru.library.model.Book;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthor(Author author);
    List<Book> findAllByRented(boolean rented);
    void deleteAllByAuthor(Author author);

}
