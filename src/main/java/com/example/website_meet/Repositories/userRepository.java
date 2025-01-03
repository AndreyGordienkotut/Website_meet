package com.example.website_meet.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.website_meet.Models.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface userRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
