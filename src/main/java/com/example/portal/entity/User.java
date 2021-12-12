package com.example.portal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    public Long getUid() {
        return uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public String getEmergency_number() {
        return emergency_number;
    }
    public void setEmergency_number(String emergency_number) {
        this.emergency_number = emergency_number;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;   
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean vaccinated = true;
    @Column(nullable = false)
    private String emergency_number;
    @Column(nullable = false)
    private String skills;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "add_fk",nullable = false)
    private Address address;

    public User(){}
    public User(String username, String name, boolean vaccinated, String emergency_number, String skills, String password, Address address) {
        this.username = username;
        this.name = name;
        this.vaccinated = vaccinated;
        this.emergency_number = emergency_number;
        this.skills = skills;
        this.password = password;
        this.address = address;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isVaccinated() {
        return vaccinated;
    }
    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
    
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Name is: " + getName() + "\n Password is :" + getPassword();
    }
}
