package com.example.portal.repository;

import com.example.portal.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

//    @Query(value = "")
//    String getPinCode(String USERNAME);
}
