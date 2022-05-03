package ru.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Book book;
    @DateTimeFormat(pattern = "mm:HH dd.MM.yyyy")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rentedDate;
    @DateTimeFormat(pattern = "mm:HH dd.MM.yyyy")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateToReturn;
    private boolean returned;
    private boolean extended;

    public Rent(User user, Book book, LocalDateTime rentedDate, LocalDateTime dateToReturn) {
        this.user = user;
        this.book = book;
        this.rentedDate = rentedDate;
        this.dateToReturn = dateToReturn;
        this.returned = false;
        this.extended = false;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public Rent() {
        this.returned = false;
        this.extended = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(LocalDateTime rentedDate) {
        this.rentedDate = rentedDate;
    }

    public LocalDateTime getDateToReturn() {
        return dateToReturn;
    }

    public void setDateToReturn(LocalDateTime dateToReturn) {
        this.dateToReturn = dateToReturn;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
