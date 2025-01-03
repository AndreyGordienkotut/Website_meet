package com.example.website_meet.Controllers;

import com.example.website_meet.Models.*;
import com.example.website_meet.Repositories.*;

import com.example.website_meet.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class Register {
    private final userRepository userRepository;
    private final genderRepository genderRepository;
    private final profileRepository profileRepository;
    private final photoRepository photoRepository;
    private final orientationRepository orientationRepository;
    private final cityRepository cityRepository;
    private final interest_websiteRepository interest_websiteRepository;
    private final profileHasPhotoRepository profileHasPhotoRepository;
    private final profile_tagRepository profile_tagRepository;


    private final tagRepository tagRepository;


    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";
    @Autowired
    private final HttpSession session;
    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            model.addAttribute("error", "Login does not exist");
            return "login";
        } else {
            if (!user.getPassword().equals(password)) {
                model.addAttribute("error", "Incorrent login or password");
                return "login";
            } else {
                session.setUser(user);
                return "redirect:/Main";
            }
        }
    }

    @PostMapping("/registration")
    public String register(@RequestParam String login,
                           @RequestParam String password,
                           @RequestParam(required = false) Long genderId,
                           @RequestParam String name,
                           @RequestParam LocalDate birthDate,
                           @RequestParam String description,
                           @RequestParam(required = false) Integer growth,
                           @RequestParam(required = false) String work,
                           @RequestParam(required = false) Boolean alcohol,
                           @RequestParam(required = false) Boolean smoking,
                           @RequestParam(required = false) Long orientationId,
                           @RequestParam Long cityId,
                           @RequestParam(required = false) Long Interest_websiteId,
                           @RequestParam("photos") MultipartFile[] photos,
                           @RequestParam(required = false, value = "tags") List<Long> tagIds,
                           Model model) {
        // Create and save the user
        User newUser = new User(login, password);
        userRepository.save(newUser);
        System.out.println("Received Gender ID: " + genderId);
        // Create and save the profile
        Profile profile = new Profile();
        profile.setUser(newUser);
        Gender gender = null;
        if (genderId != null) {
            gender = genderRepository.findById(genderId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid gender ID"));
        }
        profile.setGender(gender);

        profile.setName(name);
        profile.setBirthDate(birthDate);
        profile.setDescription(description);
        profile.setGrowth(growth);
        if (work != null && work.trim().isEmpty()) {
            work = null;
        }
        profile.setWork(work);
        profile.setAlcohol(alcohol != null ? alcohol : false);
        profile.setSmoking(smoking != null ? smoking : false);
        Orientation orientation = null;
        if (orientationId != null) {
            orientation = orientationRepository.findById(orientationId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid orientation ID"));
        }
        profile.setOrientation(orientation);
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid city ID"));
        profile.setCity(city);
        Interest_website interest_website = null;
        if (Interest_websiteId != null) {
            interest_website = interest_websiteRepository.findById(Interest_websiteId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid interestWebsite ID"));
        }
        profile.setInterest_website(interest_website);
        profile.setCount_complaint(0);

        profileRepository.save(profile);
        if (tagIds != null) {
            for (Long tagId : tagIds) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid tag ID: " + tagId));
                Profile_tag profileTag = new Profile_tag(profile, tag);
                profile_tagRepository.save(profileTag);
            }
        }

        int sequence = 1;
        boolean hasUploadedPhotos = false;
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                hasUploadedPhotos = true;
                try {
                    String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                    Path path = Paths.get(UPLOADED_FOLDER + fileName);
                    Files.write(path, photo.getBytes());

                    Photo newPhoto = new Photo(fileName);
                    photoRepository.save(newPhoto);

                    Profile_Photo profilePhoto = new Profile_Photo(profile, newPhoto, sequence++);
                    profileHasPhotoRepository.save(profilePhoto);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Если фото не выбраны, добавить фото по умолчанию
        if (!hasUploadedPhotos) {
            Photo defaultPhoto = photoRepository.findBySrc("userlogo.png");
            if (defaultPhoto == null) {
                defaultPhoto = new Photo("userlogo.png");
                photoRepository.save(defaultPhoto);
            }
            Profile_Photo profilePhoto = new Profile_Photo(profile, defaultPhoto, sequence);
            profileHasPhotoRepository.save(profilePhoto);
        }



        // Store the user in the session
        session.setUser(newUser);

        // Redirect to the main page after successful registration
        return "redirect:/Main";
    }

    @GetMapping("/registration")
    public String registerShow(Model model) {
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "registration"; // Возвращаем страницу регистрации
    }
    @GetMapping("/logout")
    public String logout(Model model){
        if (session.isPresent()){
            session.clearUser();
            return "redirect:/"; }
        return "redirect:/";
    }

    @GetMapping("/")
    public String showMain(Model model) {
        return "index"; // Начальная страница для неавторизованных пользователей
    }
}