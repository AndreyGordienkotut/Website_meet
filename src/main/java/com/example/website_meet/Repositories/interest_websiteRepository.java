package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Interest_website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface interest_websiteRepository extends JpaRepository<Interest_website,Long> {

}