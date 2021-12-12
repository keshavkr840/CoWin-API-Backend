package com.example.portal.User;

import com.example.portal.entity.Address;
import com.example.portal.entity.User;
import com.example.portal.repository.AddressRepository;
import com.example.portal.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfUserExistsByUserName(){
        //given
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
           "sarath12345","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        userRepository.save(user);

        //when
        User exists = userRepository.findByUsername("sarath12345");

        //then
        assertThat(exists).isEqualTo(user);
    }
    @Test
    void itShouldCheckIfUserDoesNotExistsByUserName(){
        //given
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "sarath12345","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        userRepository.save(user);

        //when
        User exists = userRepository.findByUsername("sarath123456");

        //then
        assertThat(exists).isNotEqualTo(user);
    }

}
