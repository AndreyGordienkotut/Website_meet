package com.example.website_meet.Controllers;

import com.example.website_meet.Models.*;
import com.example.website_meet.Repositories.*;

import com.example.website_meet.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class profileController {
    @Autowired
    private final HttpSession session;
    private final userRepository userRepository;
    private final profileRepository profileRepository;
    private final photoRepository photoRepository;
    private final profileHasPhotoRepository profileHasPhotoRepository;
    private final profile_tagRepository profile_tagRepository;
    private final complaintsRepository complaintsRepository;
    private final cityRepository cityRepository;
    private final orientationRepository orientationRepository;
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";
    @GetMapping("/profile")
    public String showProfile(Model model) {
        Long currentUserId = session.getUser().getId();

        if (currentUserId == null) {
            return "redirect:/login"; // Перенаправляем на страницу входа, если пользователь не авторизован
        }

        Optional<User> user = userRepository.findById(currentUserId);
        if (user.isPresent()) {
            Profile profile = profileRepository.findByUserId(currentUserId);

            // Получаем фотографии профиля из таблицы Profile_has_photo
            List<Photo> photos = photoRepository.findPhotosByUserId(currentUserId);

            model.addAttribute("profile", profile);
            model.addAttribute("photos", photos); // Добавляем фотографии в модель
            List<Tag> tags = profile_tagRepository.findTagsByProfileId(profile.getUser().getId());
            model.addAttribute("tags", tags);
            return "profile"; // Имя шаблона для отображения профиля
        } else {
            return "redirect:/profile";
        }
    }
    @GetMapping("/changeProfile")
    public String showChangeProfilePage(Model model) {
        User currentUser = session.getUser();
        Long currentUserId = session.getUser().getId();
        Profile profile = profileRepository.findByUserId(currentUserId);
        List<Photo> photos = photoRepository.findPhotosByUserId(currentUser.getId());
        List<Complaints> complaints = complaintsRepository.findAll();
        List<Tag> tags = profile_tagRepository.findTagsByProfileId(currentUser.getId());

        model.addAttribute("tags", tags);
        // Добавляем данные в модель для отображения на странице
        model.addAttribute("profile", profile);
        model.addAttribute("photos", photos);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("complaints", complaints);
        model.addAttribute("profile", profile);
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("orientations", orientationRepository.findAll());
        return "changeProfile";
    }
    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam Map<String, String> params,
                                @RequestParam("photos") MultipartFile[] photos) {

        Long currentUserId = session.getUser().getId();

        Profile profile = profileRepository.findByUserId(currentUserId);

        // Обновление данных профиля
        profile.setBirthDate(LocalDate.parse(params.get("birthDate")));
        profile.setDescription(params.get("description"));
        profile.setGrowth(Integer.parseInt(params.getOrDefault("growth", "0")));
        profile.setWork(params.get("work"));
        profile.setAlcohol(params.containsKey("alcohol"));
        profile.setSmoking(params.containsKey("smoking"));
        profile.setCity(cityRepository.findById(Long.parseLong(params.get("cityId")))
                .orElseThrow(() -> new IllegalArgumentException("Invalid city ID")));
        profile.setOrientation(orientationRepository.findById(Long.parseLong(params.get("orientationId")))
                .orElseThrow(() -> new IllegalArgumentException("Invalid orientation ID")));

        List<Photo> existingPhotos = profileHasPhotoRepository.findPhotosByProfile(profile);


        // Обработка новых фотографий
        for (int i = 0; i < photos.length; i++) {
            MultipartFile photo = photos[i];

            if (!photo.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                    Path path = Paths.get(UPLOADED_FOLDER + fileName);
                    Files.write(path, photo.getBytes());

                    Photo photoEntity;

                    if (i < existingPhotos.size()) {
                        // Обновляем существующую фотографию
                        photoEntity = existingPhotos.get(i);
                        photoEntity.setSrc(fileName);
                    } else {
                        // Создаем новую фотографию
                        photoEntity = new Photo(fileName);
                        photoRepository.save(photoEntity);

                        Profile_Photo profilePhoto = new Profile_Photo(profile, photoEntity, i + 1);
                        profileHasPhotoRepository.save(profilePhoto);
                    }

                    photoRepository.save(photoEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        profileRepository.save(profile);
        return "redirect:/profile";
    }
}





