package com.example.portal.info;

import javax.persistence.Entity;

import static com.example.portal.utils.EncodingPassword.ncoder;

public class LoginInfo {
    private String username;
    private String password;
    
    public LoginInfo(String username, String password) {
        this.username = username;
        //encrypt
        String encpass = ncoder(password);
        this.password = encpass;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
