package ru.library.dto;

public class RentDto {

    private Long userId;
    private Long bookId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "RentDto{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
