package com.example.hotel.parcelmanagement;

import com.example.hotel.parcelmanagement.model.Parcel;
import com.example.hotel.parcelmanagement.repository.ParcelRepository;
import com.example.hotel.parcelmanagement.service.ParcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParcelServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private ParcelService parcelService;

    @BeforeEach
    void setUp() {
    }
    @Test
    void addParcelForGuest() {
        Parcel parcel = new Parcel();
        parcel.setDescription("Test Parcel");
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel);

        Parcel savedParcel = parcelService.addParcelForGuest(1L, parcel);

        assertNotNull(savedParcel);
        verify(parcelRepository).save(parcel);
    }
    @Test
    void markParcelAsPickedUp_WhenParcelExists() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        parcel.setPickedUp(false);
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));

        boolean result = parcelService.markParcelAsPickedUp(1L);

        assertTrue(result);
        verify(parcelRepository).save(parcel);
        assertTrue(parcel.isPickedUp());
    }

    @Test
    void markParcelAsPickedUp_WhenParcelDoesNotExist() {
        when(parcelRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean result = parcelService.markParcelAsPickedUp(999L);

        assertFalse(result);
        verify(parcelRepository, never()).save(any(Parcel.class));
    }
    @Test
    void findParcelsForGuest() {
        List<Parcel> parcels = Arrays.asList(new Parcel(), new Parcel());
        when(parcelRepository.findByGuestId(1L)).thenReturn(parcels);

        List<Parcel> foundParcels = parcelService.findParcelsForGuest(1L);

        assertFalse(foundParcels.isEmpty());
        assertEquals(2, foundParcels.size());
        verify(parcelRepository).findByGuestId(1L);
    }
    @Test
    void findAllParcels() {
        List<Parcel> parcels = Arrays.asList(new Parcel(), new Parcel());
        when(parcelRepository.findAll()).thenReturn(parcels);

        List<Parcel> allParcels = parcelService.findAllParcels();

        assertFalse(allParcels.isEmpty());
        assertEquals(2, allParcels.size());
        verify(parcelRepository).findAll();
    }
    @Test
    void deleteParcel_WhenParcelExists() {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));

        boolean result = parcelService.deleteParcel(1L);

        assertTrue(result);
        verify(parcelRepository).delete(parcel);
    }

    @Test
    void deleteParcel_WhenParcelDoesNotExist() {
        when(parcelRepository.findById(anyLong())).thenReturn(Optional.empty());

        boolean result = parcelService.deleteParcel(999L);

        assertFalse(result);
        verify(parcelRepository, never()).delete(any(Parcel.class));
    }



}
