package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Profile_complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface profile_complaintsRepository extends JpaRepository<Profile_complaints,Long> {

}