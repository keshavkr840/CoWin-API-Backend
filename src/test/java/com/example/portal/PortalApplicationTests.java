package com.example.portal;

import com.example.portal.repository.AddressRepository;
import com.example.portal.repository.UserRepository;
import com.example.portal.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// @SpringBootTest
public class PortalApplicationTests {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@MockBean
	private AddressRepository addressRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		System.out.println("Test Case Working");
	}

	@Test
	public void getAllUsersTest(){

	}
}
