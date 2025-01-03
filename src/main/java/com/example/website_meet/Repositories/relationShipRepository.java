package com.example.website_meet.Repositories;

import com.example.website_meet.Models.Profile;
import com.example.website_meet.Models.Relation;
import com.example.website_meet.Models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface relationShipRepository extends JpaRepository<Relationship, Long> {
    // Найти всех пользователей, кроме тех, кого оценил текущий пользователь
    @Query("SELECT p FROM Profile p WHERE p.user_id NOT IN " +
            "(SELECT r.id.relationshipUserId FROM Relationship r WHERE r.id.userId = :currentUserId)")
    List<Profile> findAllExcludingRated(Long currentUserId);
    @Query("SELECT r.id.relationshipUserId FROM Relationship r WHERE r.user.id = :currentUserId")
    List<Long> findRatedUserIdsByUserId(@Param("currentUserId") Long currentUserId);
    Optional<Relationship> findByIdUserIdAndIdRelationshipUserIdAndRelationId(Long userId, Long relationshipUserId, Long relationId);
}