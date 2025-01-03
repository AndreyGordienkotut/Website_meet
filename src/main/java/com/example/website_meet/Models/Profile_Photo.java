package com.example.website_meet.Models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_photo")
public class Profile_Photo {
    @EmbeddedId
    private Profile_Photo_id id;

    @ManyToOne
    @MapsId("profileId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Profile profile;

    @ManyToOne
    @MapsId("PhotoId")
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    private Photo photo;

    @Column(name = "sequence" ,nullable = false)
    private int sequence;

    public Profile_Photo() {}

    public Profile_Photo(Profile profile, Photo photo, int sequence) {
        this.profile = profile;
        this.photo = photo;
        this.sequence = sequence;
        this.id = new Profile_Photo_id(profile.getUser().getId(), photo.getId());
    }

    // Геттеры и сеттеры


    public Profile_Photo_id getId() {
        return id;
    }

    public void setId(Profile_Photo_id id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
