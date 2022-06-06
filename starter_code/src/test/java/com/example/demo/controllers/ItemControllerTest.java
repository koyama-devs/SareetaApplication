package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    @Test
    public void getItems_ResponseOK() {
        ResponseEntity<List<Item>> response = itemController.getItems();
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getItemById_ResponseOK() {
        // Mock Item Already Exist
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item()));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getItemById_ResponseNotFound() {
        ResponseEntity<Item> response = itemController.getItemById(1L);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getItemsByName_ResponseOK() {
        // Mock List Item Already Exist
        when(itemRepository.findByName(anyString())).thenReturn(List.of(new Item()));

        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getItemsByName_ResponseNotFound() {
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}