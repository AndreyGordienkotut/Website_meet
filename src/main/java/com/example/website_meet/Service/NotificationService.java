package com.example.website_meet.Service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private final Map<Long, List<String>> userNotifications = new HashMap<>();

    public void addNotification(Long userId, String message) {
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(message);
    }

    public List<String> getNotifications(Long userId) {
        return userNotifications.getOrDefault(userId, new ArrayList<>());
    }

    public void clearNotifications(Long userId) {
        userNotifications.remove(userId);
    }
}
