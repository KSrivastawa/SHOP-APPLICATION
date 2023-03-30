package com.shop.controller;

import com.shop.entity.Users;
import com.shop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private UserRepo userRepo;

    @Autowired
    public LoginController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/signIn")
    public ResponseEntity<Users> getLoggedInUserHandler(Authentication auth){

        Users user = userRepo.findByUserEmail(auth.getName()).orElseThrow(()->new BadCredentialsException("User not found"));

        return new ResponseEntity<Users>(user, HttpStatus.ACCEPTED);
    }



}
