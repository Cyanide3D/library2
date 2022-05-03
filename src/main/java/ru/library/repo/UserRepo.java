package ru.library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.library.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
