package com.example.ParckvanSystemBackend.Controllers;

import com.example.ParckvanSystemBackend.DTOs.JwtResponse;
import com.example.ParckvanSystemBackend.DTOs.LoginRequest;
import com.example.ParckvanSystemBackend.Entities.User;
import com.example.ParckvanSystemBackend.Repositories.UserRepository;
import com.example.ParckvanSystemBackend.Security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(loginRequest.getPassword())) { // Compare plaintext passwords
                String token = jwtTokenUtil.generateToken(user.getUsername(),user.getId());

                // Construct a response object
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("status", 200);
                responseBody.put("token", token);
                responseBody.put("userId", user.getId());

                return ResponseEntity.ok(responseBody);
            }
        }

        // Construct an error response
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", 401);
        errorBody.put("message", "Invalid username or password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }
}
