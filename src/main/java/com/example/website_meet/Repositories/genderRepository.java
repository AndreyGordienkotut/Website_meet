package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface genderRepository extends JpaRepository<Gender,Long> {

}
