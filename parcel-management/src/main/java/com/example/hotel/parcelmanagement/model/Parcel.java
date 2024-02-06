package com.example.hotel.parcelmanagement.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long guestId; // Stores guestId directly, adhering to microservices principles

    private String description;
    private LocalDateTime receivedDate;
    private boolean pickedUp;

    // Default constructor
    public Parcel() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
}
