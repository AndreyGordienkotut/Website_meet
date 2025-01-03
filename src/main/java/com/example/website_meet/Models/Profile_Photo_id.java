package com.example.website_meet.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Profile_Photo_id implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "photo_id")
    private Long photoId;

    public Profile_Photo_id() {}

    public Profile_Photo_id(Long userId, Long photoId) {
        this.userId = userId;
        this.photoId = photoId;
    }

    // Геттеры и сеттеры

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile_Photo_id that = (Profile_Photo_id) o;
        return Objects.equals(userId, that.userId) && Objects.equals(photoId, that.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, photoId);
    }
}