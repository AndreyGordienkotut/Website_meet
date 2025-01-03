package com.example.website_meet.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_complaints")
public class Profile_complaints {
    @EmbeddedId
    private Profile_complaints_id id;

    @ManyToOne
    @MapsId("profileId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Profile profile;
    @ManyToOne
    @MapsId("userSentId")
    @JoinColumn(name = "user_sent_id", referencedColumnName = "user_id")
    private Profile userSent;

    @ManyToOne
    @MapsId("ComplaintsId")
    @JoinColumn(name = "complaints_id", referencedColumnName = "id")
    private Complaints complaints;

    @Column(name = "date",nullable = false)
    private LocalDateTime date;

    public Profile_complaints() {}

    public Profile_complaints(Profile profile,Profile userSent, Complaints complaints,LocalDateTime date) {
        this.profile = profile;
        this.userSent = userSent;
        this.complaints = complaints;
        this.date=date;
        this.id = new Profile_complaints_id(profile.getUser().getId(), userSent.getUser_id(),complaints.getId());
    }

    public Profile_complaints_id getId() {
        return id;
    }

    public void setId(Profile_complaints_id id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getUserSent() {
        return userSent;
    }

    public void setUserSent(Profile userSent) {
        this.userSent = userSent;
    }

    public Complaints getComplaints() {
        return complaints;
    }

    public void setComplaints(Complaints complaints) {
        this.complaints = complaints;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
