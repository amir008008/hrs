package com.example.hotel.guestmanagement.controller;

import com.example.hotel.guestmanagement.model.Guest;
import com.example.hotel.guestmanagement.service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GuestControllerTest {

    @InjectMocks
    private GuestController guestController;

    @Mock
    private GuestService guestService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckOutGuest() {
        // Arrange
        Long guestId = 123L;
        Guest checkedOutGuest = createGuest("Alice", "Johnson");
        when(guestService.checkOutGuest(guestId)).thenReturn(checkedOutGuest);

        // Act
        ResponseEntity<?> response = guestController.checkOutGuest(guestId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(checkedOutGuest, response.getBody());
    }

    @Test
    public void testCheckOutGuestNotFound() {
        Long guestId = 456L;
        when(guestService.checkOutGuest(guestId)).thenThrow(new RuntimeException("Guest not found with id " + guestId));

        ResponseEntity<?> response = guestController.checkOutGuest(guestId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Guest with ID " + guestId + " not found."));
    }


    @Test
    public void testDeleteGuest() {
        // Arrange
        Long guestId = 789L;
        doNothing().when(guestService).deleteGuest(guestId);

        // Act
        ResponseEntity<?> response = guestController.deleteGuest(guestId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("deleted successfully."));
    }

    @Test
    public void testDeleteGuestNotFound() {
        Long guestId = 101L;
        doThrow(new RuntimeException("Guest not found with id " + guestId)).when(guestService).deleteGuest(guestId);

        ResponseEntity<?> response = guestController.deleteGuest(guestId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Guest not found with id " + guestId));
    }



    // Helper method to create a guest with default values
    private Guest createGuest(String firstName, String lastName) {
        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        // Set other properties as needed
        return guest;
    }
    @Test
    public void testCheckInGuest() {
        // Arrange
        Guest guest = createGuest("John", "Doe");
        when(guestService.checkInGuest(any(Guest.class))).thenReturn(guest);

        // Act
        ResponseEntity<?> response = guestController.checkInGuest(guest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guest, response.getBody());
    }

    @Test
    public void testCheckInGuestFailure() {
        // Arrange
        Guest guest = createGuest("Error", "Guest");
        when(guestService.checkInGuest(any(Guest.class))).thenThrow(new RuntimeException("Check-in conflict"));

        // Act
        ResponseEntity<?> response = guestController.checkInGuest(guest);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Check-in conflict"));
    }
    @Test
    public void testFindAllGuests() {
        // Arrange
        List<Guest> guests = new ArrayList<>();
        guests.add(createGuest("Alice", "Johnson"));
        guests.add(createGuest("Bob", "Smith"));
        when(guestService.findAllGuests()).thenReturn(guests);

        // Act
        List<Guest> response = guestController.findAllGuests();

        // Assert
        assertEquals(2, response.size());
    }
    @Test
    public void testFindAllCurrentGuests() {
        // Arrange
        List<Guest> currentGuests = new ArrayList<>();
        currentGuests.add(createGuest("Current", "Guest"));
        when(guestService.findAllCurrentGuests()).thenReturn(currentGuests);

        // Act
        List<Guest> response = guestController.findAllCurrentGuests();

        // Assert
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }
    @Test
    public void testUpdateGuest() {
        // Arrange
        Long guestId = 123L;
        Guest originalGuest = createGuest("Original", "Guest");
        Guest updatedGuest = createGuest("Updated", "Guest");
        when(guestService.updateGuest(eq(guestId), any(Guest.class))).thenReturn(updatedGuest);

        // Act
        ResponseEntity<?> response = guestController.updateGuest(guestId, originalGuest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGuest, response.getBody());
    }

    @Test
    public void testUpdateGuestNotFound() {
        // Arrange
        Long guestId = 404L;
        Guest guestDetails = createGuest("Nonexistent", "Guest");
        when(guestService.updateGuest(eq(guestId), any(Guest.class))).thenThrow(new RuntimeException("Guest not found"));

        // Act
        ResponseEntity<?> response = guestController.updateGuest(guestId, guestDetails);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Guest not found"));
    }

}
