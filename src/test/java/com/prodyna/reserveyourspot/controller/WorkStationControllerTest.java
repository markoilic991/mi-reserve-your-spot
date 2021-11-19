package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
  public void should_Add_New_WorkStation() throws Exception {


    WorkStation workStationNew = new WorkStation(1, "PD002211");
    workStationNew.setId(1);
    workStationNew.setUniqueCode("PD0011111");


    Mockito.when(workStationService.save(any(WorkStation.class))).thenReturn(workStationNew);

    mockMvc.perform(MockMvcRequestBuilders.post("/workStations/")
                    .content(objectMapper.writeValueAsString(workStationNew))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

  }

  @Test
  public void should_Find_WorkStation_By_Id() throws Exception {

    WorkStation workStationNew = new WorkStation(1, "PD002211");
    workStationNew.setUniqueCode("PD002211");

    Mockito.when(workStationService.findById(anyInt())).thenReturn(java.util.Optional.of(workStationNew));

    mockMvc.perform(MockMvcRequestBuilders.get("/workStations/2"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueCode").value("PD002211"))
            .andExpect(status().isOk());

  }

  @Test
  public void should_Find_All_WorkStations() throws Exception {

    List<WorkStation> stationsList = new ArrayList<>();
    stationsList.add(new WorkStation(1, "PD002211"));
    stationsList.add(new WorkStation(2, "PD000031"));
    stationsList.add(new WorkStation(3, "PD004411"));

    Mockito.when(workStationService.findAll()).thenReturn(stationsList);

    mockMvc.perform(MockMvcRequestBuilders.get("/workStations/"))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void when_Station_Is_Invalid_Then_Return_Exception400() throws Exception {

    WorkStation workStation = new WorkStation(1, null);

    String body = objectMapper.writeValueAsString(workStation);

    mockMvc.perform(MockMvcRequestBuilders.post("/workStations/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());
  }

}
