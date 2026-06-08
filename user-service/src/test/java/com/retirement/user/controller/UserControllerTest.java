package com.retirement.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retirement.user.entity.User;
import com.retirement.user.service.UserService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddUser() throws Exception {

        User user = new User(
                1L,
                "Chethan",
                "chethan@gmail.com",
                25,
                60);

        when(service.saveUser(user)).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))

                .andExpect(status().isOk())
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print());
    }

    @Test
    void testGetUsers() throws Exception {

        User user = new User(
                1L,
                "Chethan",
                "chethan@gmail.com",
                25,
                60);

        when(service.getAllUsers())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))

                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                .andExpect(status().isOk());}

    @Test
    void testGetUserById() throws Exception {

        User user = new User(
                1L,
                "Chethan",
                "chethan@gmail.com",
                25,
                60);

        when(service.getUserById(1L))
                .thenReturn(user);

        mockMvc.perform(get("/users/1"))

                .andExpect(status().isOk())
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print());
    }
}