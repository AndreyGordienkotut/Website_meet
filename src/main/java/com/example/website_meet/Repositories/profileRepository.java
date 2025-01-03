package com.example.website_meet.Repositories;
import com.example.website_meet.Models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface profileRepository extends JpaRepository<Profile, Long> {

    // Найти всех пользователей, исключая тех, кого оценил текущий пользователь
    @Query("SELECT p FROM Profile p WHERE p.user.id <> :currentUserId AND p.user.id NOT IN " +
            "(SELECT r.relationship_user.id FROM Relationship r WHERE r.user.id = :currentUserId)")
    List<Profile> findAllExcludingRated(@Param("currentUserId") Long currentUserId);

    Profile findByUserId(Long userId);
    //List<Profile> findProfilesByBirthDateBetween(LocalDate startDate, LocalDate endDate);
    List<Profile> findByBirthDateBetween(LocalDate minBirthDate, LocalDate maxBirthDate);
    List<Profile> findByBirthDateBetweenAndGenderId(LocalDate minBirthDate, LocalDate maxBirthDate, Long genderId);
}