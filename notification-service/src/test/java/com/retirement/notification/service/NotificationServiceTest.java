package com.retirement.notification.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.retirement.notification.dto.NotificationRequest;

public class NotificationServiceTest {

    private NotificationService service =
            new NotificationService();

    @Test
    void testSendNotification() {

        NotificationRequest request =
                new NotificationRequest();

        request.setUserId(1L);
        request.setMessage(
                "Contribution received successfully");

        String response =
                service.sendNotification(request);

        assertNotNull(response);

        assertTrue(
                response.contains("Notification Sent To User"));

        assertTrue(
                response.contains("Contribution received successfully"));
    }
}