package ru.library.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.library.model.Rent;
import ru.library.service.NotificationService;
import ru.library.service.RentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendNotificationManager {

    @Autowired
    private RentService rentService;
    @Autowired
    private NotificationService notificationService;
    private final List<Long> notificatedPreDay = new ArrayList<>();
    private final List<Long> notificatedAfter = new ArrayList<>();

    @Scheduled(fixedDelay = 3000)
    public void notification() {
        final List<Rent> rents = rentService.getAllRents();
        final LocalDateTime currentDate = LocalDateTime.now();

        for (Rent rent : rents) {
            if (rent.isReturned()) {
                continue;
            }

            if (currentDate.isAfter(rent.getDateToReturn()) && !notificatedAfter.contains(rent.getId())) {
                notificationService.makeNotification(rent.getUser(), "Время на бронирование книги " + rent.getBook().getName() + " вышло. Продлите бронь в профиле либо же верните книгу в библиотеку.");
                notificatedAfter.add(rent.getId());
            }

            if (currentDate.isBefore(rent.getDateToReturn()) && currentDate.isAfter(rent.getDateToReturn().minusDays(1)) && !notificatedPreDay.contains(rent.getId())) {
                notificationService.makeNotification(rent.getUser(), "Вам остался всего один день для того что бы вернуть книгу " + rent.getBook().getName() + " в библиотеку.");
                notificatedPreDay.add(rent.getId());
            }
        }
    }

}
