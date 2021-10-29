package com.pluralsight.reserve_your_spot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.service.OfficeRoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfficeRoomController.class)
public class OfficeRoomControllerTest {

    @Autowired
    private OfficeRoomController officeRoomController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OfficeRoomService officeRoomService;

    @MockBean
    private OfficeRoom officeRoom;

    @MockBean
    private WorkStation workStation;

    @Test
    public void addOfficeTest() throws Exception {

        OfficeRoom officeRoomNew = new OfficeRoom();
        officeRoomNew.setId(1);
        officeRoomNew.setName("JAVA");
        officeRoomNew.setOrderNo(23);

        Mockito.when(officeRoomService.addRoom(any(OfficeRoom.class))).thenReturn(officeRoomNew);

        mockMvc.perform(MockMvcRequestBuilders.post("/rooms/")
                .content(objectMapper.writeValueAsString(officeRoomNew))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(23));
    }

    @Test
    public void getOfficeByIdTest() throws Exception {

        OfficeRoom officeRoomNew = new OfficeRoom();
        officeRoomNew.setName("JAVA");
        officeRoomNew.setOrderNo(23);

        Mockito.when(officeRoomService.getById(anyInt())).thenReturn(officeRoomNew);

        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(23))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRoomsTest() throws Exception {

        List<OfficeRoom> roomsList = new ArrayList<>();
        roomsList.add(new OfficeRoom(1, "JAVA", 23));
        roomsList.add(new OfficeRoom(2, "QA", 14));
        roomsList.add(new OfficeRoom(3, ".NET", 10));

        Mockito.when(officeRoomService.getAll()).thenReturn(roomsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateRoomTest() throws Exception {

        OfficeRoom officeRoomNew = new OfficeRoom("FRONT END", 10);
        officeRoomNew.setName("JAVA");
        officeRoomNew.setOrderNo(15);

        Mockito.when(officeRoomService.update(any(OfficeRoom.class))).thenReturn(officeRoomNew);

        mockMvc.perform(MockMvcRequestBuilders.put("/rooms/update")
                        .content(objectMapper.writeValueAsString(officeRoomNew))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(15));



    }
}
