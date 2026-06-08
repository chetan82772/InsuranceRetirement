package com.retirement.notification.service;

import org.springframework.stereotype.Service;

import com.retirement.notification.dto.NotificationRequest;

@Service
public class NotificationService {

    public String sendNotification(NotificationRequest request) {

        String output = "Notification Sent To User "
                + request.getUserId()
                + " : "
                + request.getMessage();

        System.out.println(output);

        return output;
    }
}