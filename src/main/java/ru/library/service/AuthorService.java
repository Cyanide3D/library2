package ru.library.service;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.model.Author;
import ru.library.repo.AuthorRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private BookService bookService;

    public Author getAuthorById(Long id) {
        return authorRepo.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {
        final Author author = getAuthorById(id);
        bookService.deleteAllByAuthor(author);
        authorRepo.delete(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public Author save(Author author) {
        return authorRepo.save(author);
    }

}
