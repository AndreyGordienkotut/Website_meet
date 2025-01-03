package com.example.website_meet.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.website_meet.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface photoRepository extends JpaRepository<Photo,Long> {
    Photo findBySrc(String src);
    //@Query("SELECT p FROM Photo p JOIN Profile_Photo pp ON p.id = pp.photo.id WHERE pp.profile.user.id = :userId ORDER BY pp.sequence")
    //List<Photo> findPhotosByUserId(Long userId);
//    @Query("SELECT p FROM Photo p JOIN Profile_Photo pp ON p.id = pp.photo.id WHERE pp.profile.user.id = :userId ORDER BY pp.sequence")
//    List<Photo> findPhotosByProfileUserId(@Param("userId") Long userId);
@Query("SELECT p FROM Photo p JOIN Profile_Photo pp ON p.id = pp.photo.id WHERE pp.profile.user_id = :userId ORDER BY pp.sequence")
List<Photo> findPhotosByUserId(@Param("userId") Long userId);
}
