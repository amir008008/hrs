package com.example.hotel.parcelmanagement;

import com.example.hotel.parcelmanagement.controller.ParcelController;
import com.example.hotel.parcelmanagement.model.Parcel;
import com.example.hotel.parcelmanagement.service.ParcelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParcelController.class)
public class ParcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelService parcelService;

    @Test
    public void testAddParcelForGuest() throws Exception {
        Parcel parcel = new Parcel();
        parcel.setId(1L);
        parcel.setGuestId(1L);
        parcel.setDescription("A package for guest");
        parcel.setReceivedDate(java.time.LocalDateTime.now());
        parcel.setPickedUp(false);

        when(parcelService.addParcelForGuest(any(Long.class), any(Parcel.class))).thenReturn(parcel);

        mockMvc.perform(MockMvcRequestBuilders.post("/parcels/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"A package for guest\",\"receivedDate\":\"2023-01-01T12:00:00\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("A package for guest"));
    }
    @Test
    public void testDeleteParcel_Success() throws Exception {
        when(parcelService.deleteParcel(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/parcels/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteParcel_NotFound() throws Exception {
        when(parcelService.deleteParcel(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/parcels/delete/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testFindParcelsForGuest_Found() throws Exception {
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(new Parcel()); // Add at least one parcel to simulate found parcels

        when(parcelService.findParcelsForGuest(any(Long.class))).thenReturn(parcels);

        mockMvc.perform(MockMvcRequestBuilders.get("/parcels/guest/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists()); // Checks if at least one parcel is returned
    }

    @Test
    public void testFindParcelsForGuest_NotFound() throws Exception {
        when(parcelService.findParcelsForGuest(any(Long.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/parcels/guest/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testMarkParcelAsPickedUp_Success() throws Exception {
        when(parcelService.markParcelAsPickedUp(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/parcels/pickup/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testMarkParcelAsPickedUp_NotFound() throws Exception {
        when(parcelService.markParcelAsPickedUp(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/parcels/pickup/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFindAllParcels() throws Exception {
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(new Parcel()); // Assume there's at least one parcel in the system

        when(parcelService.findAllParcels()).thenReturn(parcels);

        mockMvc.perform(MockMvcRequestBuilders.get("/parcels/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists()); // Checks if at least one parcel is returned
    }


}
