package ru.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.library.dto.ExtendRentDto;
import ru.library.dto.RentDto;
import ru.library.model.Book;
import ru.library.model.Rent;
import ru.library.model.User;
import ru.library.repo.RentRepo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentService {

    @Autowired
    private RentRepo rentRepo;
    @Autowired
    private AuthService authService;
    @Autowired
    private BookService bookService;

    @Transactional
    public void cancelRent(Long bookId) {
        final Book book = bookService.getBookById(bookId);
        final Rent rent = getRentByBook(book);

        book.setRented(false);
        rent.setReturned(true);

        bookService.update(book);
        rentRepo.save(rent);
    }

    public void extendRent(ExtendRentDto dto) {
        final Book book = bookService.getBookById(dto.getBookId());
        final Rent rent = getRentByBook(book);

        if (rent.isExtended()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        rent.setDateToReturn(rent.getDateToReturn().plusDays(dto.getDays()));
        rent.setExtended(true);
        rentRepo.save(rent);
    }

    public List<Rent> getAllRents() {
        return rentRepo.findAll();
    }

    public Rent getRentByBookId(Long id) {
        return getRentByBook(bookService.getBookById(id));
    }

    public Rent getRentByBook(Book book) {
        return rentRepo.findByBook(book).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Rent save(RentDto dto) {
        final User user = authService.getUserById(dto.getUserId());
        final Book book = bookService.getBookById(dto.getBookId());

        final LocalDateTime currentDate = LocalDateTime.now();
        final LocalDateTime dateToReturn = currentDate.plusDays(30);

        final Rent rent = new Rent(user, book, currentDate, dateToReturn);
        book.setRented(true);

        bookService.update(book);
        return rentRepo.save(rent);
    }

    public List<Rent> getRentsByUser(User user) {
        return rentRepo.findAllByUser(user);
    }

}
