package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Orientation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orientationRepository extends JpaRepository<Orientation,Long> {

}