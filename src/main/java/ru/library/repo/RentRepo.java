package ru.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.Book;
import ru.library.model.Rent;
import ru.library.model.User;

import java.util.List;
import java.util.Optional;

public interface RentRepo extends JpaRepository<Rent, Long> {

    List<Rent> findAllByUser(User user);
    Optional<Rent> findByBook(Book book);

}
