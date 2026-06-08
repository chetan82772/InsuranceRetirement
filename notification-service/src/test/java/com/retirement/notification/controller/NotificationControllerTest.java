package com.retirement.notification.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retirement.notification.dto.NotificationRequest;
import com.retirement.notification.service.NotificationService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSendNotification() throws Exception {

        NotificationRequest request =
                new NotificationRequest();

        request.setUserId(1L);

        request.setMessage(
                "Contribution received successfully");

        when(service.sendNotification(
                any(NotificationRequest.class)))

                .thenReturn(
                        "Notification Sent To User 1 : Contribution received successfully");

        mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Notification Sent To User 1 : Contribution received successfully"));
    }
}