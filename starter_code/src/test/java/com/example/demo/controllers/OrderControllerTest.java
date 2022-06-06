package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void submit_UserNull_ResponseNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit("user");
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void submit_ResponseOK() {
        User user = new User();
        Cart cart = new Cart();
        cart.addItem(new Item(1L, "item", new BigDecimal(13.5), ""));
        user.setCart(cart);
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit("user");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getOrdersForUser_UserNull_ResponseNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("user");
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getOrdersForUser_ResponseOK() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User());
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("user");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}