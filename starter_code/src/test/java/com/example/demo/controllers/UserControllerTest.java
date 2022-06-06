package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserController userController;

    @Test
    public void findById_ResponseOK() {
        // Mock Username Already Exist
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        ResponseEntity<User> response = userController.findById(1L);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findById_ResponseNotFound() {
        ResponseEntity<User> response = userController.findById(1L);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByName_ResponseOK() {
        // Mock Username Already Exist
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        ResponseEntity<User> response = userController.findByUserName("user");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findByName_ResponseNotFound() {
        ResponseEntity<User> response = userController.findByUserName("user");
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createUser_ResponseOK() {
        // Input param
        String username = "user";
        String password = "pass123456";
        String confirmPassword = "pass123456";
        String encodedPassword = "bCryptEncodedPassword";

        // Mock encodedPassword
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encodedPassword);

        // Create request
        CreateUserRequest createUserRequest = new CreateUserRequest(username, password, confirmPassword);
        ResponseEntity<User> createUserResponse = userController.createUser(createUserRequest);
        User createdUser = createUserResponse.getBody();

        // Assert
        Assert.assertNotNull(createUserResponse);
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(HttpStatus.OK, createUserResponse.getStatusCode());
        Assert.assertEquals(username, createUserResponse.getBody().getUsername());
        Assert.assertEquals(encodedPassword, createdUser.getPassword());
    }

    @Test
    public void createUser_PasswordLengthLessThan7_ResponseBadRequest() {
        // Input param
        String username = "user";
        String password = "123";
        String confirmPassword = "123";

        // Create request
        CreateUserRequest createUserRequest = new CreateUserRequest(username, password, confirmPassword);
        ResponseEntity<User> createUserResponse = userController.createUser(createUserRequest);
        User createdUser = createUserResponse.getBody();

        // Assert
        Assert.assertEquals(HttpStatus.BAD_REQUEST, createUserResponse.getStatusCode());
        Assert.assertNull(createdUser);
    }

    @Test
    public void createUser_PasswordNotEqualsConfirmPassword_ResponseBadRequest() {
        // Input param
        String username = "user";
        String password = "1234567";
        String confirmPassword = "123";

        // Create request
        CreateUserRequest createUserRequest = new CreateUserRequest(username, password, confirmPassword);
        ResponseEntity<User> createUserResponse = userController.createUser(createUserRequest);
        User createdUser = createUserResponse.getBody();

        // Assert
        Assert.assertEquals(HttpStatus.BAD_REQUEST, createUserResponse.getStatusCode());
        Assert.assertNull(createdUser);
    }

    @Test
    public void createUser_UsernameAlreadyExist_ResponseBadRequest() {
        // Input param
        String username = "user";
        String password = "1234567";
        String confirmPassword = "1234567";

        // Mock Username Already Exist
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        // Create user request
        CreateUserRequest createUserRequest = new CreateUserRequest(username, password, confirmPassword);
        ResponseEntity<User> createUserResponse = userController.createUser(createUserRequest);
        User createdUser = createUserResponse.getBody();

        // Assert
        Assert.assertEquals(HttpStatus.BAD_REQUEST, createUserResponse.getStatusCode());
        Assert.assertNull(createdUser);
    }
}