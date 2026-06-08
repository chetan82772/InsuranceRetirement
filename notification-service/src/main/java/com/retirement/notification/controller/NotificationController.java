package com.retirement.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.retirement.notification.dto.NotificationRequest;
import com.retirement.notification.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping
    public String sendNotification(@RequestBody NotificationRequest request) {

        return service.sendNotification(request);
    }
}