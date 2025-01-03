package com.example.website_meet.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Profile {
    @Id

    @Column(name = "user_id",unique = true)
    private Long user_id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = true)
    private Gender gender;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date" , nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private Integer growth;

    @Column(nullable = true)
    private String work;

    @Column(nullable = true)
    private Boolean alcohol;

    @Column(nullable = true)
    private Boolean smoking;
    @ManyToOne
    @JoinColumn(name = "orientation_id", referencedColumnName = "id", nullable = true)
    private Orientation orientation;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    @ManyToOne
    @JoinColumn(name = "interest_website_id", referencedColumnName = "id", nullable = true)
    private Interest_website Interest_website;
    @Column(nullable = false)
    private int count_complaint;



    public Profile(){}
    public Profile(Gender gender,String name, LocalDate birthDate, String description, int growth, String work, boolean alcohol, boolean smoking,Orientation orientation, City city,Interest_website Interest_website, int count_complaint) {
        this.gender=gender;
        this.name = name;
        this.birthDate = birthDate;
        this.description = description;
        this.growth = growth;
        this.work = work;
        this.alcohol = alcohol;
        this.smoking = smoking;
        this.orientation=orientation;
        this.city= city;
        this.Interest_website=Interest_website;
        this.count_complaint=count_complaint;

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Boolean isAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Boolean alcohol) {
        this.alcohol = alcohol;
    }

    public Boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Interest_website getInterest_website() {
        return Interest_website;
    }

    public void setInterest_website(Interest_website Interest_website) {
        this.Interest_website = Interest_website;
    }

    public int getCount_complaint() {
        return count_complaint;
    }

    public void setCount_complaint(int count_complaint) {
        this.count_complaint = count_complaint;
    }
}
