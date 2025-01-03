package com.example.website_meet.Service;

import com.example.website_meet.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.website_meet.Repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class profileService {

    @Autowired
    private profileRepository profileRepository;

    @Autowired
    private relationShipRepository relationshipRepository; // Репозиторий для получения отношений

    public List<Profile> findProfilesByBirthDateExcludingRated(LocalDate minBirthDate, LocalDate maxBirthDate, Long currentUserId) {

        // Находим все профили, подходящие по дате рождения
        List<Profile> profiles = profileRepository.findByBirthDateBetween(minBirthDate, maxBirthDate);

        // Находим всех пользователей, которых текущий пользователь уже оценил
        List<Long> ratedUserIds = relationshipRepository.findRatedUserIdsByUserId(currentUserId);

        // Фильтруем профили, исключая те, которые пользователь уже оценил или которые принадлежат самому пользователю
        return profiles.stream()
                .filter(profile -> !ratedUserIds.contains(profile.getUser().getId()) && !profile.getUser().getId().equals(currentUserId))
                .collect(Collectors.toList());
    }
    public List<Profile> findProfilesByBirthDateAndGenderExcludingRated(LocalDate minBirthDate, LocalDate maxBirthDate, Long currentUserId, Long genderId) {

        // Находим все профили, подходящие по дате рождения и полу
        List<Profile> profiles;
        if (genderId != null) {
            profiles = profileRepository.findByBirthDateBetweenAndGenderId(minBirthDate, maxBirthDate, genderId);
        } else {
            profiles = profileRepository.findByBirthDateBetween(minBirthDate, maxBirthDate);
        }

        // Находим всех пользователей, которых текущий пользователь уже оценил
        List<Long> ratedUserIds = relationshipRepository.findRatedUserIdsByUserId(currentUserId);

        // Фильтруем профили, исключая те, которые пользователь уже оценил или которые принадлежат самому пользователю
        return profiles.stream()
                .filter(profile -> !ratedUserIds.contains(profile.getUser().getId()) && !profile.getUser().getId().equals(currentUserId))
                .collect(Collectors.toList());
    }
}
