package com.example.website_meet.Controllers;
import com.example.website_meet.bean.HttpSession;
import com.example.website_meet.Models.*;
import com.example.website_meet.Repositories.*;

import com.example.website_meet.Service.NotificationService;


import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
public class NotificationController {

    @Autowired
    private final HttpSession session;
    private final NotificationService notificationService;
    private final userRepository userRepository;




    @GetMapping("/relationship")
    public String showNotifications(Model model) {
        // Получаем текущего пользователя
        Long currentUserId = session.getUser().getId();



        // Получаем уведомления для пользователя
        List<String> notifications = notificationService.getNotifications(currentUserId);

        // Добавляем уведомления в модель
        model.addAttribute("notifications", notifications);

        // Отображаем страницу
        return "relationship";
    }
}