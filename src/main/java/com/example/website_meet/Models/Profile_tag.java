package com.example.website_meet.Models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_tag")
public class Profile_tag {
    @EmbeddedId
    private Profile_tag_id id;

    @ManyToOne
    @MapsId("profileId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Profile profile;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;


    public Profile_tag() {}

    public Profile_tag(Profile profile, Tag tag) {
        this.profile = profile;
        this.tag = tag;
        this.id = new Profile_tag_id(profile.getUser().getId(), tag.getId());
    }


    // Геттеры и сеттеры


    public Profile_tag_id getId() {
        return id;
    }

    public void setId(Profile_tag_id id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
