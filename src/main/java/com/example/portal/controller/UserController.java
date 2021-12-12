package com.example.portal.controller;

import java.util.*;

import javax.validation.Valid;

import com.example.portal.info.LoginInfo;
import com.example.portal.entity.User;

import com.example.portal.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userservice;

    //Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userservice.getAllUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
    return userservice.createUser(user);
}

    @PostMapping("/login")
    public ResponseEntity<String> checkLogin(@RequestBody LoginInfo login){
       return userservice.checkLogin(login);
    }

//    @GetMapping("/cowin/{USERNAME}")
//    public String returnCenters(@PathVariable String USERNAME){
//        return userservice.returnCenters(USERNAME);
//    }

    @GetMapping("/cowin1/{USERNAME}")
    public JSONObject returnCenters1(@PathVariable String USERNAME){
        return userservice.returnCenters1(USERNAME);
    }
}

