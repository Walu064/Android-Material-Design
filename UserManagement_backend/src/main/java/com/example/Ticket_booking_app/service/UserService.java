package com.example.Ticket_booking_app.service;

import com.example.Ticket_booking_app.model.User;
import com.example.Ticket_booking_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(String userLogin) {
        return userRepository.findByUserLogin(userLogin);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }
}