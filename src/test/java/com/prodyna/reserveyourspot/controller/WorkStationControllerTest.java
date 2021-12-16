package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  @Autowired
  private WorkStationService workStationService;

  @MockBean
  @Autowired
  private WorkStationRepository workStationRepository;

  @MockBean
  private WorkStation workStation;

  @MockBean
  private OfficeRoom officeRoom;

  WorkStation workStationWindows;
  WorkStation workStationLinux;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    workStationWindows = new WorkStation();
    workStationWindows.setId(1);
    workStationWindows.setUniqueCode("PD00002");
    workStationWindows.setDescription("Windows WorkStation");
    workStationLinux = new WorkStation();
    workStationLinux.setId(2);
    workStationLinux.setUniqueCode("PD11145");
    workStationLinux.setDescription("Linux WorkStation");

  }

  @AfterEach
  public void cleanUp() {

    workStationRepository.deleteAll();

  }

  @Test
  public void should_Add_New_WorkStation() throws Exception {

    Mockito.when(workStationService.save(any(WorkStation.class))).thenReturn(workStationLinux);

    mockMvc.perform(MockMvcRequestBuilders.post("/workStations/")
                    .content(objectMapper.writeValueAsString(workStationLinux))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

  }

  @Test
  public void should_Find_WorkStation_By_Id() throws Exception {

    Mockito.when(workStationService.findById(anyInt())).thenReturn((workStationWindows));

    mockMvc.perform(MockMvcRequestBuilders.get("/workStations/2"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.uniqueCode").value("PD00002"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Windows WorkStation"))
            .andExpect(status().isOk());

  }

  @Test
  public void should_Find_All_WorkStations() throws Exception {

    Mockito.when(workStationService.findAll())
            .thenReturn((List<WorkStation>) Stream.of(workStationLinux, workStationWindows).collect(Collectors.toList()));

    mockMvc.perform(MockMvcRequestBuilders.get("/workStations/"))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void when_Station_Is_Invalid_Then_Return_Exception400() throws Exception {

    workStationWindows.setUniqueCode(null);

    String body = objectMapper.writeValueAsString(workStationWindows);

    mockMvc.perform(MockMvcRequestBuilders.post("/workStations/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());
  }

}
