package com.example.hotel.guestmanagement.repository;

import com.example.hotel.guestmanagement.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    @Query("SELECT g FROM Guest g WHERE g.checkOutDate > CURRENT_TIMESTAMP OR g.checkOutDate IS NULL")
    List<Guest> findCurrentGuests();

    List<Guest> findAll();

    Optional<Guest> findByRoomNumberAndCheckOutDateIsNull(String roomNumber);
}

