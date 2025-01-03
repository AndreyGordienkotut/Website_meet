package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Profile;
import com.example.website_meet.Models.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface relationRepository extends JpaRepository<Relation, Long> {
}