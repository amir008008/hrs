package com.example.hotel.parcelmanagement.service;

import com.example.hotel.parcelmanagement.model.Parcel;
import com.example.hotel.parcelmanagement.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {
    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public Parcel addParcelForGuest(Long guestId, Parcel parcel) {
        parcel.setGuestId(guestId); // Set guestId directly without fetching Guest entity
        parcel.setReceivedDate(LocalDateTime.now());
        parcel.setPickedUp(false); // Initially, parcels are not picked up
        return parcelRepository.save(parcel);
    }

    public boolean markParcelAsPickedUp(Long parcelId) {
        return parcelRepository.findById(parcelId).map(parcel -> {
            parcel.setPickedUp(true);
            parcelRepository.save(parcel);
            return true;
        }).orElse(false);
    }

    public List<Parcel> findParcelsForGuest(Long guestId) {
        return parcelRepository.findByGuestId(guestId);
    }

    public List<Parcel> findAllParcels() {
        return parcelRepository.findAll();
    }

    public boolean deleteParcel(Long parcelId) {
        Optional<Parcel> parcelOptional = parcelRepository.findById(parcelId);
        if (parcelOptional.isPresent()) {
            Parcel parcel = parcelOptional.get();
            parcelRepository.delete(parcel);
            return true;
        } else {
            return false; // Parcel with the given ID not found
        }
    }
}
