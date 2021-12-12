package com.example.portal.service;

import com.example.portal.entity.Address;
import com.example.portal.info.LoginInfo;
import com.example.portal.entity.User;
import com.example.portal.repository.AddressRepository;
import com.example.portal.repository.UserRepository;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository,addressRepository);
    }

    @Test
    //@Disabled
    void getAllUsers(){
        //when
        userServiceImpl.getAllUsers();
        //then
        verify(userRepository).findAll();
    }

    @Test
    void canCreateUser() {
        //given
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "","SarathKrishna",true,"1111111111","react, java","",address
        );
        User user1 = new User(
                "sarathkrishna","SarathKrishna",true,"1111111111","react, java","",address
        );
        User user2 = new User(
                "","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        User user3 = new User(
                "sarathkrishna1","SarathKrishna",true,"1111111111","react, java",
                "RandomPa@123",address
        );
        User user4 = new User(
                "sarathkrishna1","SarathKrishna",true,"1111111111","react, java",
                "RandomPa@123",address
        );

        //when
        ResponseEntity<String> response = userServiceImpl.createUser(user);    //response has username and password randomly generated
        ResponseEntity<String> response1 = userServiceImpl.createUser(user1);  //response1 has password randomly generated
        ResponseEntity<String> response2 = userServiceImpl.createUser(user2);  //response2 has username randomly generated
        ResponseEntity<String> response3 = userServiceImpl.createUser(user3);  //response3 just gets "Registered Successfully" response
        given(userRepository.findByUsername("sarathkrishna1"))             // mocking for response4 where createUser(user4) throws !=null for findByUsername
                .willReturn(user3);
        ResponseEntity<String> response4 = userServiceImpl.createUser(user4);  //user4 and user3 are same so the response should be "User Name Already Taken"


        //then
        assertEquals(202,response.getStatusCodeValue());
        assertEquals(202,response1.getStatusCodeValue());
        assertEquals(202,response2.getStatusCodeValue());
        assertEquals(202,response3.getStatusCodeValue());
        assertEquals(400,response4.getStatusCodeValue());
        assertEquals("User Name Already Taken",response4.getBody());
    }

    @Test
    void canCreateUserWithRandomUsername() {
        //given
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        //when
        userServiceImpl.createUser(user);

        assertThat(user.getUsername()).isNotEqualTo("");
    }

    @Test
    void canCreateUserWithRandomPassword() {
        //given
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "sarath12345","SarathKrishna",true,"1111111111","react, java","",address
        );
        //when
        userServiceImpl.createUser(user);

        assertThat(user.getPassword()).isNotEqualTo("");
    }

    @Test
    void canCheckLoginSuccesful(){
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "sarath1234567","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        LoginInfo login = new LoginInfo("sarath1234567","RandomPassword@123");
        //when
        userServiceImpl.createUser(user);

        given(userRepository.findByUsername(login.getUsername()))
                .willReturn(user);

        ResponseEntity<String> response = userServiceImpl.checkLogin(login);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void canCheckLoginFailureBecauseOfUsername(){
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "sarath1234567","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        LoginInfo login = new LoginInfo("sarath123","RandomPassword@123");
        //when
        userServiceImpl.createUser(user);

        given(userRepository.findByUsername(login.getUsername()))
                .willReturn(null);

        ResponseEntity<String> response = userServiceImpl.checkLogin(login);
        assertEquals(403, response.getStatusCodeValue());
        assertEquals("username doesn't exist",response.getBody());
    }

    @Test
    void canCheckLoginFailureBecauseOfPassword(){
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "sarath1234567","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        LoginInfo login = new LoginInfo("sarath1234567","RandomPassword@1234");
        //when
        userServiceImpl.createUser(user);

        given(userRepository.findByUsername(login.getUsername()))
                .willReturn(user);

        ResponseEntity<String> response = userServiceImpl.checkLogin(login);
        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Password doesnt Match",response.getBody());
    }

    @Test
    void returnCentersForInvalidPincode() {
        Address address = new Address(
                "NTR Statue","Random","Ongole","011111","AP","","","","",""
        );
        User user = new User(
                "sarath1234567","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        userServiceImpl.createUser(user);
        given(userRepository.findByUsername("sarath1234567"))
                .willReturn(user);
        JSONObject actualList = userServiceImpl.returnCenters1("sarath1234567");
        System.out.println(actualList);
        System.out.println(actualList);
        JSONObject expected = new JSONObject();
        expected.put("sessions", new JSONArray());
        //assertEquals(expected["sessions"],actualList.sessions);
    }

    @Test
    void returnCentersFoValidPincode() {
        Address address = new Address(
                "NTR Statue","Random","Ongole","523001","AP","","","","",""
        );
        User user = new User(
                "sarath1234567","SarathKrishna",true,"1111111111","react, java","RandomPassword@123",address
        );
        userServiceImpl.createUser(user);
        given(userRepository.findByUsername("sarath1234567"))
                .willReturn(user);
        JSONObject actualList = userServiceImpl.returnCenters1("sarath1234567");
        System.out.println(actualList);
        System.out.println(actualList);
        //assertNotEquals("{}",actualList);
    }
}