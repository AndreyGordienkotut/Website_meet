package com.example.website_meet.Controllers;

import com.example.website_meet.Models.*;
import com.example.website_meet.Repositories.*;
import com.example.website_meet.Service.*;
import com.example.website_meet.bean.HttpSession;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@AllArgsConstructor
@Controller
public class SearchHuman {
    @Autowired
    private final HttpSession session;
    private final userRepository userRepository;
    private final profileRepository profileRepository;
    private final photoRepository photoRepository;
    private final relationShipRepository relationshipRepository;
    private final relationRepository relationRepository;
    private final NotificationService notificationService;
    private final profileService profileService;
    private final profile_complaintsRepository profile_complaintsRepository;
    private final complaintsRepository complaintsRepository;
    private final tagRepository tagRepository;
    private final profile_tagRepository profile_tagRepository;
    @PostMapping("/search")
    public String searchHuman(Model model) {
        // Получаем список всех пользователей
        List<Profile> allUsers = profileRepository.findAll();

        // Выбираем случайного пользователя
        if (allUsers.size() > 0) {
            Profile randomUser = allUsers.get(new Random().nextInt(allUsers.size()));


            // Получаем профиль выбранного пользователя
            Profile profile = profileRepository.findByUserId(randomUser.getUser_id());


            List<Photo> photos = photoRepository.findPhotosByUserId(randomUser.getUser_id());

            // Добавляем данные в модель для отображения на странице
            model.addAttribute("profile", profile);
            model.addAttribute("photos", photos);
        }

        return "Main";
    }
    @GetMapping("/Main")
    public String searchHumans(Model model) {
        // Получаем всех пользователей, кроме тех, кого уже оценил текущий пользователь
        Long currentUserId = session.getUser().getId();
        List<Profile> allUsers = profileRepository.findAllExcludingRated(currentUserId);

        if (!allUsers.isEmpty()) {
            // Выбираем случайного пользователя
            Profile randomUser = allUsers.get(new Random().nextInt(allUsers.size()));
            List<Photo> photos = photoRepository.findPhotosByUserId(randomUser.getUser_id());
            List<Tag> tags = profile_tagRepository.findTagsByProfileId(randomUser.getUser().getId());
            model.addAttribute("tags", tags);
            // Добавляем данные в модель для отображения на странице
            model.addAttribute("profile", randomUser);
            model.addAttribute("photos", photos);
            model.addAttribute("currentUserId", currentUserId);
            List<Complaints> complaints = complaintsRepository.findAll();
            model.addAttribute("complaints", complaints);

        } else {
            model.addAttribute("profile", null);
        }

        return "Main";
    }
    @PostMapping("/filterByAge")
    public String filterByAge(
            @RequestParam("ageMin") int ageMin,
            @RequestParam("ageMax") int ageMax,
            Model model) {

        Long currentUserId = session.getUser().getId();
        int currentYear = LocalDate.now().getYear();
        LocalDate maxBirthDate = LocalDate.now().minusYears(ageMin);
        LocalDate minBirthDate = LocalDate.now().minusYears(ageMax);

        // Поиск пользователей с учетом фильтра и исключенных оцененных пользователей
        List<Profile> filteredProfiles = profileService.findProfilesByBirthDateExcludingRated(
                minBirthDate, maxBirthDate, currentUserId);

        if (!filteredProfiles.isEmpty()) {
            Profile randomUser = filteredProfiles.get(new Random().nextInt(filteredProfiles.size()));
            List<Photo> photos = photoRepository.findPhotosByUserId(randomUser.getUser_id());
            List<Complaints> complaints = complaintsRepository.findAll();
            List<Tag> tags = profile_tagRepository.findTagsByProfileId(randomUser.getUser().getId());
            model.addAttribute("tags", tags);
            model.addAttribute("complaints", complaints);
            model.addAttribute("profile", randomUser);
            model.addAttribute("photos", photos);
            model.addAttribute("currentUserId", currentUserId);
        } else {
            model.addAttribute("profile", null);
        }

        List<String> notifications = notificationService.getNotifications(currentUserId);
        model.addAttribute("notifications", notifications);

        return "Main";
    }
    @PostMapping("/filterByAgeAndGender")
    public String filterByAgeAndGender(
            @RequestParam("ageMin") int ageMin,
            @RequestParam("ageMax") int ageMax,
            @RequestParam(value = "genderId", required = false) Long genderId,
            Model model) {

        Long currentUserId = session.getUser().getId();
        LocalDate maxBirthDate = LocalDate.now().minusYears(ageMin);
        LocalDate minBirthDate = LocalDate.now().minusYears(ageMax);

        List<Profile> filteredProfiles;

        if (genderId == null) {
            filteredProfiles = profileService.findProfilesByBirthDateExcludingRated(
                    minBirthDate, maxBirthDate, currentUserId);
        } else {
            filteredProfiles = profileService.findProfilesByBirthDateAndGenderExcludingRated(
                    minBirthDate, maxBirthDate, currentUserId, genderId);
        }

        if (!filteredProfiles.isEmpty()) {
            Profile randomUser = filteredProfiles.get(new Random().nextInt(filteredProfiles.size()));
            List<Photo> photos = photoRepository.findPhotosByUserId(randomUser.getUser_id());
            List<Complaints> complaints = complaintsRepository.findAll();
            List<Tag> tags = profile_tagRepository.findTagsByProfileId(randomUser.getUser().getId());
            model.addAttribute("tags", tags);
            model.addAttribute("complaints", complaints);
            model.addAttribute("profile", randomUser);
            model.addAttribute("photos", photos);
            model.addAttribute("currentUserId", currentUserId);
        } else {
            model.addAttribute("profile", null);
        }



        return "Main";
    }


    @PostMapping("/relationship")
    public String addRelationship(@RequestParam Long currentUserId, @RequestParam Long otherUserId, @RequestParam Long relationId) {
        Optional<Relation> relationOpt = relationRepository.findById(relationId);
        if (relationOpt.isPresent()) {
            Optional<User> currentUser = userRepository.findById(currentUserId);
            Optional<User> otherUser = userRepository.findById(otherUserId);

            if (currentUser.isPresent() && otherUser.isPresent()) {
                Relation relation = relationOpt.get();
                Relationship relationship = new Relationship(
                        currentUser.get(),
                        otherUser.get(),
                        relation,
                        LocalDateTime.now()
                );

                if (relationship.getRelation() != null) {
                    relationshipRepository.save(relationship);

                    // Проверка на взаимный лайк
                    if (relationId == 1L) { // только для лайков
                        Optional<Relationship> reciprocalLike = relationshipRepository
                                .findByIdUserIdAndIdRelationshipUserIdAndRelationId(otherUserId, currentUserId, 1L);

                        if (reciprocalLike.isPresent()) {
                            String message = "Вы понравились друг другу, может, пообщаетесь!";
                            notificationService.addNotification(currentUserId, message + " Пользователь: " + otherUserId);
                            notificationService.addNotification(otherUserId, message + " Пользователь: " + currentUserId);
                        }
                    }
                } else {
                    System.out.println("Ошибка: relation не должен быть null");
                }
            } else {
                System.out.println("Пользователь не найден");
            }
        } else {
            System.out.println("Отношение с ID " + relationId + " не найдено");
        }

        return "redirect:/Main?currentUserId=" + currentUserId;
    }
    @PostMapping("/like")
    public String likeUser(@RequestParam Long currentUserId, @RequestParam Long likedUserId) {
        return addRelationship(currentUserId, likedUserId, 1L); // 1L - ID для "like"
    }

    @PostMapping("/dislike")
    public String dislikeUser(@RequestParam Long currentUserId, @RequestParam Long dislikedUserId) {
        return addRelationship(currentUserId, dislikedUserId, 2L); // 2L - ID для "dislike"
    }
    @PostMapping("/skip")
    public String skipUser(@RequestParam Long currentUserId, Model model) {
        // Достаем параметры фильтрации, которые были установлены последним запросом фильтра
        int ageMin = 18; // Установите минимальный возраст, если хотите значение по умолчанию
        int ageMax = 65; // Установите максимальный возраст, если хотите значение по умолчанию
        Long genderId = null; // Установите гендер, если хотите значение по умолчанию

        LocalDate maxBirthDate = LocalDate.now().minusYears(ageMin);
        LocalDate minBirthDate = LocalDate.now().minusYears(ageMax);

        List<Profile> filteredProfiles;

        // Повторяем фильтрацию без учета пропущенного профиля
        if (genderId == null) {
            filteredProfiles = profileService.findProfilesByBirthDateExcludingRated(
                    minBirthDate, maxBirthDate, currentUserId);
        } else {
            filteredProfiles = profileService.findProfilesByBirthDateAndGenderExcludingRated(
                    minBirthDate, maxBirthDate, currentUserId, genderId);
        }

        if (!filteredProfiles.isEmpty()) {
            Profile randomUser = filteredProfiles.get(new Random().nextInt(filteredProfiles.size()));
            List<Photo> photos = photoRepository.findPhotosByUserId(randomUser.getUser_id());
            List<Complaints> complaints = complaintsRepository.findAll();
            List<String> notifications = notificationService.getNotifications(currentUserId);
            List<Tag> tags = profile_tagRepository.findTagsByProfileId(randomUser.getUser().getId());
            model.addAttribute("tags", tags);
            model.addAttribute("complaints", complaints);
            model.addAttribute("notifications", notifications);
            model.addAttribute("profile", randomUser);
            model.addAttribute("photos", photos);
            model.addAttribute("currentUserId", currentUserId);
        } else {
            model.addAttribute("profile", null);
        }

        List<String> notifications = notificationService.getNotifications(currentUserId);
        model.addAttribute("notifications", notifications);

        return "Main";
    }
    @PostMapping("/complaint")
    public String sendComplaint(@RequestParam Long currentUserId,
                                @RequestParam Long userId,
                                @RequestParam Long complaintId,
                                Model model) {
        Profile currentUser = profileRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));
        Profile reportedUser = profileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Reported user not found"));

        Complaints complaint = complaintsRepository.findById(complaintId)
                .orElseThrow(() -> new EntityNotFoundException("Complaint not found"));

        if (currentUser == null || reportedUser == null || complaint == null) {
            model.addAttribute("error", "Invalid data");
            return "error";
        }

        Profile_complaints profileComplaint = new Profile_complaints(currentUser, reportedUser, complaint, LocalDateTime.now());
        profile_complaintsRepository.save(profileComplaint);
        Relation relation = relationRepository.findById(3L)
                .orElseThrow(() -> new EntityNotFoundException("Relation not found"));
        Relationship relationship = new Relationship(currentUser.getUser(), reportedUser.getUser(), relation, LocalDateTime.now());
        relationshipRepository.save(relationship);
        List<Profile> filteredProfiles = profileRepository.findAllExcludingRated(currentUserId);
        if (!filteredProfiles.isEmpty()) {
            Profile randomUser = filteredProfiles.get(new Random().nextInt(filteredProfiles.size()));
            List<Photo> photos = photoRepository.findPhotosByUserId(randomUser.getUser_id());
            List<Complaints> complaints = complaintsRepository.findAll();
            List<String> notifications = notificationService.getNotifications(currentUserId);
            List<Tag> tags = profile_tagRepository.findTagsByProfileId(randomUser.getUser().getId());
            model.addAttribute("tags", tags);
            model.addAttribute("complaints", complaints);
            model.addAttribute("notifications", notifications);
            model.addAttribute("profile", randomUser);
            model.addAttribute("photos", photos);
            model.addAttribute("currentUserId", currentUserId);
        } else {
            model.addAttribute("profile", null);
        }

        model.addAttribute("message", "Complaint sent successfully!");
        return "Main";
    }



}
