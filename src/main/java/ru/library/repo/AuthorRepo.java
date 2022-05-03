package ru.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.Author;

public interface AuthorRepo extends JpaRepository<Author, Long> {

    Author findByName(String name);

}
