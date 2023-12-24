package com.springSecurity.LoginSystem.controller;

import com.springSecurity.LoginSystem.model.OurUser;
import com.springSecurity.LoginSystem.model.Product;
import com.springSecurity.LoginSystem.repository.ProductRepository;
import com.springSecurity.LoginSystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/public")
    public String getUser() {
        return "public page";
    }

    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody OurUser ourUser) {
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        OurUser user = userRepository.save(ourUser);
        if (user.getId() > 0) {
            return ResponseEntity.ok("user saved");
        }
        return ResponseEntity.status(404).body("Error!! User not save");
    }


    @GetMapping("/users/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<OurUser>>getAllusers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
    @GetMapping("/get-one")
    @PreAuthorize("hasAnyAuthority('ADMIN') or hasAnyAuthority('USER')")
    public ResponseEntity<Object>getMyDetails(){
        return ResponseEntity.ok(userRepository.findByMail(getLoggedUser().getUsername()));

    }
    public UserDetails getLoggedUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return  null;
    }
}