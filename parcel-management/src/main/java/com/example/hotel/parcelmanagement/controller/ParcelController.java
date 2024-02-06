package com.example.hotel.parcelmanagement.controller;

import com.example.hotel.parcelmanagement.model.Parcel;
import com.example.hotel.parcelmanagement.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/parcels")
public class ParcelController {
    private static final Logger logger = LoggerFactory.getLogger(ParcelController.class);

    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping("/add/{guestId}")
    public ResponseEntity<Parcel> addParcelForGuest(@PathVariable Long guestId, @RequestBody Parcel parcel) {
        logger.info("Received request to add parcel for guest with ID: {}", guestId);
        Parcel addedParcel = parcelService.addParcelForGuest(guestId, parcel);
        logger.info("Parcel added successfully for guest with ID: {}", guestId);
        return ResponseEntity.ok(addedParcel);
    }
    @DeleteMapping("/delete/{parcelId}")
    public ResponseEntity<?> deleteParcel(@PathVariable Long parcelId) {
        logger.info("Received request to delete parcel with ID: {}", parcelId);
        boolean deleted = parcelService.deleteParcel(parcelId);
        if (deleted) {
            logger.info("Parcel with ID {} deleted successfully", parcelId);
            return ResponseEntity.ok().build();
        } else {
            logger.info("Parcel with ID {} not found for deletion", parcelId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<Parcel>> findParcelsForGuest(@PathVariable Long guestId) {
        logger.info("Received request to find parcels for guest with ID: {}", guestId);
        List<Parcel> parcels = parcelService.findParcelsForGuest(guestId);
        if (parcels.isEmpty()) {
            logger.info("No parcels found for guest with ID: {}", guestId);
            return ResponseEntity.notFound().build();
        }
        logger.info("Found {} parcels for guest with ID: {}", parcels.size(), guestId);
        return ResponseEntity.ok(parcels);
    }

    @PostMapping("/pickup/{parcelId}")
    public ResponseEntity<?> markParcelAsPickedUp(@PathVariable Long parcelId) {
        logger.info("Received request to mark parcel with ID {} as picked up", parcelId);
        boolean updated = parcelService.markParcelAsPickedUp(parcelId);
        if (updated) {
            logger.info("Parcel with ID {} marked as picked up successfully", parcelId);
            return ResponseEntity.ok().build();
        } else {
            logger.info("Parcel with ID {} not found for marking as picked up", parcelId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Parcel>> findAllParcels() {
        logger.info("Received request to find all parcels");
        List<Parcel> parcels = parcelService.findAllParcels();
        logger.info("Found {} parcels in total", parcels.size());
        return ResponseEntity.ok(parcels);
    }
}
