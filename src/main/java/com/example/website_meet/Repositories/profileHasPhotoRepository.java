package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Photo;
import com.example.website_meet.Models.Profile;
import com.example.website_meet.Models.Profile_Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface profileHasPhotoRepository extends JpaRepository<Profile_Photo,Long> {
    @Query("SELECT p.photo FROM Profile_Photo p WHERE p.profile = :profile ORDER BY p.sequence")
    List<Photo> findPhotosByProfile(@Param("profile") Profile profile);
    void deleteByPhoto(Photo photo);
}

