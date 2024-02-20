package com.example.ameacas_ambientais_firebase;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Threat implements Serializable {
    private Long id;
    private String address;
    private String date;
    private String description;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    @NonNull
    @Override
    public String toString() {
        return id + " " + address + " " + date + " " + description;
    }

}
