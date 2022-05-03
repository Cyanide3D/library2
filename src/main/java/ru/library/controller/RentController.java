package ru.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.library.dto.ExtendRentDto;
import ru.library.dto.RentDto;
import ru.library.model.Rent;
import ru.library.service.AuthService;
import ru.library.service.RentService;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private RentService rentService;
    @Autowired
    private AuthService authService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Rent>> getRentByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(rentService.getRentsByUser(authService.getUserById(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelRent(@PathVariable("id") Long bookId) {
        rentService.cancelRent(bookId);
    }

    @PatchMapping
    public void extendedRent(@RequestBody ExtendRentDto dto) {
        rentService.extendRent(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rent> getRentByBookId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(rentService.getRentByBookId(id));
    }

    @PostMapping
    public ResponseEntity<Rent> rent(@RequestBody RentDto dto) {
        return ResponseEntity.ok(rentService.save(dto));
    }

}
