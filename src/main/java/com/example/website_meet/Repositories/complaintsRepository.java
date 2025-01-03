package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface complaintsRepository extends JpaRepository<Complaints,Long> {

}