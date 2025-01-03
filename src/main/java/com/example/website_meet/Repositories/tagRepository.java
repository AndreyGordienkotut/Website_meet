package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tagRepository extends JpaRepository<Tag,Long> {

}