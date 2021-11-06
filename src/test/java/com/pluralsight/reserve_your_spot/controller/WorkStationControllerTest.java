package com.pluralsight.reserve_your_spot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.repository.WorkStationRepository;
import com.pluralsight.reserve_your_spot.service.WorkStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WorkStationController.class)
public class WorkStationControllerTest {

    @Autowired
    private WorkStationController workStationController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkStationService workStationService;

    @MockBean
    private WorkStationRepository workStationRepository;

    @MockBean
    private WorkStation workStation;

    @MockBean
    private OfficeRoom officeRoom;

    @Test
    public void Should_Add_New_WorkStation() throws Exception {

        WorkStation workStationNew = new WorkStation(1, "PD002211");
        workStationNew.setId(1);
        workStationNew.setUniqueCode("PD002211");

        Mockito.when(workStationService.addOne(any(WorkStation.class))).thenReturn(workStationNew);

        mockMvc.perform(MockMvcRequestBuilders.post("/stations/")
                .content(objectMapper.writeValueAsString(workStationNew))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueCode").value("PD002211"));

    }

    @Test
    public void Should_Find_WorkStation_By_Id() throws Exception {

        WorkStation workStationNew = new WorkStation(1, "PD002211");
        workStationNew.setUniqueCode("PD002211");

        Mockito.when(workStationService.getById(anyInt())).thenReturn(workStationNew);

        mockMvc.perform(MockMvcRequestBuilders.get("/stations/2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueCode").value("PD002211"))
                .andExpect(status().isOk());

    }

    @Test
    public void Should_Find_All_WorkStations() throws Exception {

        List<WorkStation> stationsList = new ArrayList<>();
        stationsList.add(new WorkStation(1, "PD002211"));
        stationsList.add(new WorkStation(2, "PD000031"));
        stationsList.add(new WorkStation(3, "PD004411"));

        Mockito.when(workStationService.getAll()).thenReturn(stationsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/stations/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void Should_Update_WorkStation() throws Exception {

        WorkStation stationNew = new WorkStation(1, "PD741474");
        stationNew.setUniqueCode("PD111000");


        Mockito.when(workStationService.update(any(),anyInt())).thenReturn(stationNew);

        mockMvc.perform(MockMvcRequestBuilders.put("/stations/2")
                        .content(objectMapper.writeValueAsString(stationNew))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueCode").value("PD111000"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.room").value(new OfficeRoom(1, "QA", 1)));
    }

    @Test
    public void when_Station_Is_Invalid_Then_Return_Exception400() throws Exception {

        WorkStation workStation = new WorkStation(1, " ");

        String body = objectMapper.writeValueAsString(workStation);

        mockMvc.perform(MockMvcRequestBuilders.post("/stations/").contentType("application/json").content(body))
                .andExpect(status().isBadRequest());
    }

}
