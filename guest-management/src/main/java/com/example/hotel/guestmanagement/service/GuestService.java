package com.example.hotel.guestmanagement.service;

import com.example.hotel.guestmanagement.model.Guest;
import com.example.hotel.guestmanagement.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest checkInGuest(Guest guest) {
        // Check if the room is already assigned and not checked out
        Optional<Guest> existingGuest = guestRepository.findByRoomNumberAndCheckOutDateIsNull(guest.getRoomNumber());
        if (existingGuest.isPresent()) {
            // Room is currently occupied, so throw an exception or handle as needed
            throw new RuntimeException("Room " + guest.getRoomNumber() + " is already occupied.");
        }
        // Room is available, proceed with check-in
        guest.setCheckInDate(LocalDateTime.now());
        // Assume checkOutDate is set in the future or upon actual check-out
        return guestRepository.save(guest);
    }


    public Guest checkOutGuest(Long guestId) {
        return guestRepository.findById(guestId).map(guest -> {
            guest.setCheckOutDate(LocalDateTime.now());
            guest.setStatus(Guest.GuestStatus.CHECKED_OUT); // Update the status to CHECKED_OUT
            return guestRepository.save(guest);
        }).orElseThrow(() -> new RuntimeException("Guest not found with id " + guestId));
    }


    public List<Guest> findAllCurrentGuests() {
        return guestRepository.findCurrentGuests();
    }

//    public Optional<Guest> findById(Long id) {
//        return guestRepository.findById(id);
//    }

    // Inside GuestService.java

    public List<Guest> findAllGuests() {
        return guestRepository.findAll();
    }


    public Guest updateGuest(Long id, Guest guestDetails) {
        return guestRepository.findById(id).map(guest -> {
            guest.setFirstName(guestDetails.getFirstName());
            guest.setLastName(guestDetails.getLastName());
            guest.setRoomNumber(guestDetails.getRoomNumber());
            // Update other fields as necessary
            return guestRepository.save(guest);
        }).orElseThrow(() -> new RuntimeException("Guest not found with id " + id));
    }

    public void deleteGuest(Long id) {
        // Check if the guest exists by ID before deleting
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
        } else {
            throw new RuntimeException("Guest not found with id " + id);
        }
    }
}
