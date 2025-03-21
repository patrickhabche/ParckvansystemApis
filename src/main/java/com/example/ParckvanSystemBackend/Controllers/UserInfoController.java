package com.example.ParckvanSystemBackend.Controllers;

import com.example.ParckvanSystemBackend.DTOs.*;
import com.example.ParckvanSystemBackend.Entities.Reservation;
import com.example.ParckvanSystemBackend.Entities.User;
import com.example.ParckvanSystemBackend.Exceptions.ReservationDateTakenException;
import com.example.ParckvanSystemBackend.Exceptions.ReservationNotFoundException;
import com.example.ParckvanSystemBackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/userinfo")

public class UserInfoController  {

    public UserRepository _userRepo;

    @Autowired
    public UserInfoController( UserRepository userRepo){
        _userRepo = userRepo;
    }


    @GetMapping("/single")
    public User GetById(int id ){
        return _userRepo.findById(id).orElse(null);
    }

    @PostMapping("/update")
    public UpdateUserProfileResponse Edit(@RequestBody  UpdateUserProfileRequest request ) throws Exception{
        UpdateUserProfileResponse  response = new UpdateUserProfileResponse();
        try {
        var userToEdit =  _userRepo.findById(request.id).orElse(null);
        userToEdit.setFirstname(request.firstname);
        userToEdit.setLastname(request.lastname);
        userToEdit.setEmail(request.email);
        userToEdit.setPhone(request.phone);
        userToEdit.setUsername(request.username);

        _userRepo.save(userToEdit);

        response.code = 200;
        response.user = userToEdit;
        }
        catch (Exception exception){
            response.code = exception.hashCode();
            response.user = null;
        }
        return response;

    }


}
