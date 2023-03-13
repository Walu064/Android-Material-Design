package com.example.Ticket_booking_app.controller;

import com.example.Ticket_booking_app.model.User;
import com.example.Ticket_booking_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    //W argumencie dodaj hasło:
    @GetMapping("/user/{userLogin}")
    public ResponseEntity<User> getUser(@PathVariable String userLogin) {
        User user = userService.getUser(userLogin);
        if (user != null) {
            System.out.println("GET: User by login.");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("users/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean success = userService.deleteUser(userId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}