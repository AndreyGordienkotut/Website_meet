package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Profile_tag;
import com.example.website_meet.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface profile_tagRepository extends JpaRepository<Profile_tag,Long> {
    @Query("SELECT pt.tag FROM Profile_tag pt WHERE pt.profile.user.id = :userId")
    List<Tag> findTagsByProfileId(@Param("userId") Long userId);
}