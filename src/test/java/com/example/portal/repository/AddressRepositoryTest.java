package com.example.portal.repository;

import com.example.portal.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void itShouldCheckIfAddressExistsByCity(){
        //given
        Address address = new Address();
    }
}