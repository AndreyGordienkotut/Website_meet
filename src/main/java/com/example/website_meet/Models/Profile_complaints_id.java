package com.example.website_meet.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Profile_complaints_id implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_sent_id")
    private Long userSentId;

    @Column(name = "complaints_id")
    private Long complaints_id;

    public Profile_complaints_id() {}

    public Profile_complaints_id(Long userId, Long userSentId, Long complaints_id) {
        this.userId = userId;
        this.userSentId = userSentId;
        this.complaints_id = complaints_id;
    }

    // Геттеры и сеттеры

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserSentId() {
        return userSentId;
    }

    public void setUserSentId(Long userSentId) {
        this.userSentId = userSentId;
    }

    public Long getComplaints_id() {
        return complaints_id;
    }

    public void setComplaints_id(Long complaints_id) {
        this.complaints_id = complaints_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile_complaints_id that = (Profile_complaints_id) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userSentId, that.userSentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userSentId);
    }
}