package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private CartController cartController;

    @Test
    public void addToCart_UserNull_ResponseNotFound() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest(null, 1L, 1);
        ResponseEntity<Cart> response = cartController.addToCart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void addToCart_ItemNotExist_ResponseNotFound() {
        // Mock Username Already Exist
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest("user", 1L, 1);
        ResponseEntity<Cart> response = cartController.addToCart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void addToCart_ResponseOK() {
        // Mock user
        User user = new User();
        user.setCart(new Cart());
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        // Mock item
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item(1L, "item", new BigDecimal(13.5), "")));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest("user", 1L, 1);
        ResponseEntity<Cart> response = cartController.addToCart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void removeFromCart_UserNull_ResponseNotFound() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest(null, 1L, 1);
        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void removeFromCart_ItemNotExist_ResponseNotFound() {
        // Mock Username Already Exist
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest("user", 1L, 1);
        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void removeFromCart_ResponseOK() {
        // Mock user
        User user = new User();
        user.setCart(new Cart());
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        // Mock item
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item(1L, "item", new BigDecimal(13.5), "")));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest("user", 1L, 1);
        ResponseEntity<Cart> response = cartController.removeFromCart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}