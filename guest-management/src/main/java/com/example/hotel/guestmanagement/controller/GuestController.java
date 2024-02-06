package com.example.hotel.guestmanagement.controller;

import com.example.hotel.guestmanagement.model.Guest;
import com.example.hotel.guestmanagement.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private static final Logger logger = LoggerFactory.getLogger(GuestController.class);
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkInGuest(@RequestBody Guest guest) {
        try {
            logger.info("Checking in guest: {}", guest);
            Guest checkedInGuest = guestService.checkInGuest(guest);
            logger.info("Checked in guest: {}", checkedInGuest);
            return ResponseEntity.ok(checkedInGuest);
        } catch (RuntimeException ex) {
            logger.error("Check-in failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<?> checkOutGuest(@PathVariable Long id) {
        logger.info("Checking out guest with ID: {}", id);
        try {
            Guest checkedOutGuest = guestService.checkOutGuest(id);
            logger.info("Checked out guest: {}", checkedOutGuest);
            return ResponseEntity.ok(checkedOutGuest);
        } catch (RuntimeException e) {
            logger.error("Error checking out guest with ID: {}", id, e);
            if (e.getMessage().contains("Guest not found with id")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guest with ID " + id + " not found.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking out guest.");
        }
    }

    @GetMapping("/all")
    public List<Guest> findAllGuests() {
        logger.info("Fetching all guests");
        List<Guest> guests = guestService.findAllGuests();
        logger.info("Found {} guests", guests.size());
        return guests;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable Long id) {
        logger.info("Deleting guest with ID: {}", id);
        try {
            guestService.deleteGuest(id);
            logger.info("Deleted guest with ID: {}", id);
            return ResponseEntity.ok("Guest with ID " + id + " deleted successfully.");
        } catch (RuntimeException e) {
            logger.error("Error deleting guest with ID: {}", id, e);
            if (e.getMessage().contains("Guest not found with id")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting guest.");
        }
    }

    @GetMapping("/current")
    public List<Guest> findAllCurrentGuests() {
        logger.info("Fetching all current guests");
        List<Guest> currentGuests = guestService.findAllCurrentGuests();
        logger.info("Found {} current guests", currentGuests.size());
        return currentGuests;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGuest(@PathVariable Long id, @RequestBody Guest guestDetails) {
        logger.info("Updating guest with ID: {}", id);
        try {
            Guest updatedGuest = guestService.updateGuest(id, guestDetails);
            logger.info("Updated guest: {}", updatedGuest);
            return ResponseEntity.ok(updatedGuest);
        } catch (RuntimeException e) {
            logger.error("Error updating guest with ID: {}", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
