package com.example.website_meet.Models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "relationship")
public class Relationship {
    @EmbeddedId
    private RelationshipId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @MapsId("relationshipId")
    @JoinColumn(name = "relationship_user_id", referencedColumnName = "id")
    private User relationship_user;

    @ManyToOne
    @MapsId("relationId")
    @JoinColumn(name = "relation_id", referencedColumnName = "id",nullable = false)
    private Relation relation;
    @Column(name = "date_like")
    private LocalDateTime dateLike;

    public Relationship() {}

    public Relationship(User user, User relationshipUser, Relation relation, LocalDateTime dateLike) {
        this.user = user;
        this.relationship_user = relationshipUser;
        this.relation = relation;
        this.dateLike = dateLike;
        this.id = new RelationshipId(user.getId(), relationshipUser.getId(), relation.getId());
    }
    // Геттеры и сеттеры

    public RelationshipId getId() {
        return id;
    }

    public void setId(RelationshipId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRelationship_user() {
        return relationship_user;
    }

    public void setRelationship_user(User relationship_user) {
        this.relationship_user = relationship_user;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public LocalDateTime getDateLike() {
        return dateLike;
    }

    public void setDateLike(LocalDateTime dateLike) {
        this.dateLike = dateLike;
    }
}
