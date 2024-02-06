package com.example.hotel.guestmanagement;

import com.example.hotel.guestmanagement.model.Guest;
import com.example.hotel.guestmanagement.repository.GuestRepository;
import com.example.hotel.guestmanagement.service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkInGuest() {
        Guest guest = new Guest();
        guest.setFirstName("John");
        guest.setLastName("Doe");
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        Guest savedGuest = guestService.checkInGuest(guest);
        assertNotNull(savedGuest.getCheckInDate());
        verify(guestRepository, times(1)).save(any(Guest.class));
    }
}
