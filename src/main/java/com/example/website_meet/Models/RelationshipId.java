package com.example.website_meet.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RelationshipId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "relationship_user_id")
    private Long relationshipUserId;

    private Long relationId;
    public RelationshipId() {}

    public RelationshipId(Long userId, Long relationshipUserId, Long relationId) {
        this.userId = userId;
        this.relationshipUserId = relationshipUserId;
        this.relationId = relationId;
    }

    // Геттеры и сеттеры

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRelationshipUserId() {
        return relationshipUserId;
    }

    public void setRelationshipUserId(Long relationshipUserId) {
        this.relationshipUserId = relationshipUserId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationshipId that = (RelationshipId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(relationshipUserId, that.relationshipUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, relationshipUserId);
    }
}