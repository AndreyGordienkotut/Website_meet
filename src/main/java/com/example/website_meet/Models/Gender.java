package com.example.website_meet.Models;

import jakarta.persistence.*;

@Entity
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Column(nullable = false)
    private String name;



    public Gender(){}
    public Gender(Long id) {
        this.id = id;
    }
    public  Gender(String name){
        this.name=name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}