package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OfficeRoomController.class)
public class OfficeRoomControllerTest {

  @Autowired
  private OfficeRoomController officeRoomController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  @Autowired
  private OfficeRoomService officeRoomService;

  @MockBean
  private WorkStationService workStationService;

  @MockBean
  @Autowired
  private OfficeRoomRepository officeRoomRepository;

  @MockBean
  private OfficeRoom officeRoom;

  OfficeRoom officeRoomJava;
  OfficeRoom officeRoomDotNet;
  WorkStation workStationOne;
  WorkStation workStationTwo;
  Set<WorkStation> workStations;
  OfficeRoom officeRoomSharePoint;
  OfficeRoom officeRoomUpdated;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    workStationOne = new WorkStation();
    workStationOne.setId(1);
    workStationOne.setCode("PD76332");
    workStationOne.setDescription("Windows Work Station");
    workStationTwo = new WorkStation();
    workStationTwo.setId(2);
    workStationTwo.setCode("PD009922");
    workStationTwo.setDescription("Windows Work Station");
    workStations = new HashSet<>();
    workStations.add(workStationOne);
    workStations.add(workStationTwo);
    officeRoomJava = new OfficeRoom();
    officeRoomJava.setId(1);
    officeRoomJava.setName("JAVA");
    officeRoomJava.setCode(4);
    officeRoomJava.setWorkStations(workStations);
    officeRoomDotNet = new OfficeRoom();
    officeRoomDotNet.setId(2);
    officeRoomDotNet.setName(".NET");
    officeRoomDotNet.setCode(3);
    officeRoomSharePoint = new OfficeRoom();
    officeRoomSharePoint.setName("SharePoint");
    officeRoomSharePoint.setCode(8);
  }

  @AfterEach
  public void cleanUp() {
    officeRoomRepository.deleteAll();
  }

  @Test
  public void should_Add_New_OfficeRoom() throws Exception {
    Mockito.when(officeRoomService.save(any(OfficeRoom.class))).thenReturn(officeRoomSharePoint);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/office-rooms/")
                    .content(objectMapper.writeValueAsString(officeRoomSharePoint))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("SharePoint"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(8));
  }

  @Test
  public void should_Find_OfficeRoom_By_Id() throws Exception {
    Mockito.when(officeRoomService.findById(anyInt())).thenReturn((officeRoomJava));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/office-rooms/1"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(4))
            .andExpect(status().isOk());
  }

  @Test
  public void should_Find_All_Rooms() throws Exception {
    Mockito.when(officeRoomService.findAll())
            .thenReturn((List<OfficeRoom>) Stream.of(officeRoomJava, officeRoomDotNet).collect(Collectors.toList()));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/office-rooms/")).andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void when_Room_Is_Invalid_Then_Return_Exception400() throws Exception {
    officeRoomJava.setName(null);
    String body = objectMapper.writeValueAsString(officeRoomJava);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/office-rooms").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void should_Update_Office_Room() {
    officeRoomUpdated = new OfficeRoom();
    officeRoomUpdated.setName("Java");
    officeRoomUpdated.setCode(officeRoomDotNet.getCode());

    Mockito.when(officeRoomService.findById(officeRoomDotNet.getId())).thenReturn(officeRoomUpdated);
  }
}
