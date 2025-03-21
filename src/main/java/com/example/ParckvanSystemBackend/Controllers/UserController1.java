package com.example.ParckvanSystemBackend.Controllers;

import com.example.ParckvanSystemBackend.DTOs.*;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import com.example.ParckvanSystemBackend.Entities.User;
import com.example.ParckvanSystemBackend.Exceptions.ReservationDateTakenException;
import com.example.ParckvanSystemBackend.Exceptions.ReservationNotFoundException;
import com.example.ParckvanSystemBackend.Repositories.UserRepository;
import com.example.ParckvanSystemBackend.Services.IReservationSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller

public class UserController1 {
    private UserRepository userService;

    @Autowired
    public UserController1(UserRepository userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        var userWithUsername = userService.findByUsername(user.getUsername());
        if (userWithUsername.isPresent()) {  // Properly check if the user exists
            model.addAttribute("error", "Username is already taken!");
            return "signup"; // Stay on the signup page
        }

        var userWithEmail = userService.findByEmail(user.getEmail());
        if (userWithEmail.isPresent()) {  // Properly check if the email exists
            model.addAttribute("error", "Email is already registered!");
            return "signup"; // Stay on the signup page
        }

        user.setCreatorUserId(user.getId());
        user.setCreationTime(LocalDateTime.now());
        userService.save(user); // Save the new user
        return "redirect:http:localhost:3000/login"; // Redirect to login on success
    }
}