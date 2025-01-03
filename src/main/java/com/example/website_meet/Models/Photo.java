package com.example.website_meet.Models;
import jakarta.persistence.*;
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "src", nullable = false)
    private String src;

    public Photo(String src) {
        this.src = src;
    }

    public Photo(){}

    public Photo(int sequence, String src) {
        this.src = src;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
