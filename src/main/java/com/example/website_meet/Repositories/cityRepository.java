package com.example.website_meet.Repositories;

import com.example.website_meet.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cityRepository extends JpaRepository<City,Long> {

}