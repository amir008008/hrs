package com.example.hotel.guestmanagement.controller;

import com.example.hotel.guestmanagement.model.Guest;
import com.example.hotel.guestmanagement.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping("/checkin")
    public Guest checkInGuest(@RequestBody Guest guest) {
        return guestService.checkInGuest(guest);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<?> checkOutGuest(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(guestService.checkOutGuest(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/current")
    public List<Guest> findAllCurrentGuests() {
        return guestService.findAllCurrentGuests();
    }
}
