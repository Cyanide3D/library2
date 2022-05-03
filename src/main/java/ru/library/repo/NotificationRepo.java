package ru.library.repo;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.library.model.Notification;
import ru.library.model.User;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    @Query("select count(e) from Notification e where e.read = false and e.user = ?1")
    long countByUser(User user);
    List<Notification> findAllByUser(User user);

}
