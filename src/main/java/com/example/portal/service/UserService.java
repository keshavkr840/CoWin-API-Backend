package com.example.portal.service;

import com.example.portal.info.LoginInfo;
import com.example.portal.entity.User;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

//@Service
public interface UserService {

    public List<User> getAllUsers();
    public ResponseEntity<String> createUser(User user);
    public ResponseEntity<String> checkLogin(LoginInfo login);
    //public String returnCenters(String USERNAME);
    public JSONObject returnCenters1(String USERNAME);
}
