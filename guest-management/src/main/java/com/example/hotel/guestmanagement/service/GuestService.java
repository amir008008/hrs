package com.example.hotel.guestmanagement.service;

import com.example.hotel.guestmanagement.model.Guest;
import com.example.hotel.guestmanagement.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest checkInGuest(Guest guest) {
        guest.setCheckInDate(LocalDateTime.now());
        // Assume checkOutDate is set in the future or upon actual check-out
        return guestRepository.save(guest);
    }

    public Guest checkOutGuest(Long guestId) {
        return guestRepository.findById(guestId).map(guest -> {
            guest.setCheckOutDate(LocalDateTime.now());
            return guestRepository.save(guest);
        }).orElseThrow(() -> new RuntimeException("Guest not found with id " + guestId));
    }

    public List<Guest> findAllCurrentGuests() {
        return guestRepository.findCurrentGuests();
    }
}
