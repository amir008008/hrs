package com.example.hotel.parcelmanagement.repository;

import com.example.hotel.parcelmanagement.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByGuestIdAndPickedUpFalse(Long guestId);
    List<Parcel> findByGuestId(Long guestId);
}
