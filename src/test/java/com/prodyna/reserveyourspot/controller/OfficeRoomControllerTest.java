package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
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
  @Autowired
  private OfficeRoomRepository officeRoomRepository;

  @MockBean
  private OfficeRoom officeRoom;

  @MockBean
  private WorkStation workStation;

  OfficeRoom newOfficeRoom;
  OfficeRoom newOfficeRoom1;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    newOfficeRoom = new OfficeRoom();
    newOfficeRoom.setId(1);
    newOfficeRoom.setName("JAVA");
    newOfficeRoom.setOrderNo(4);
    newOfficeRoom1 = new OfficeRoom();
    newOfficeRoom1.setId(2);
    newOfficeRoom1.setName(".NET");
    newOfficeRoom1.setOrderNo(3);

  }

  @AfterEach
  public void cleanUp() {

    officeRoomRepository.findAll();
    officeRoomRepository.deleteAll();

  }

  @Test
  public void should_Add_New_OfficeRoom() throws Exception {

    Mockito.when(officeRoomService.save(any(OfficeRoom.class))).thenReturn(newOfficeRoom);

    mockMvc.perform(MockMvcRequestBuilders.post("/rooms/")
                    .content(objectMapper.writeValueAsString(newOfficeRoom))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(4));
  }

  @Test
  public void should_Find_OfficeRoom_By_Id() throws Exception {

    Mockito.when(officeRoomService.findById(anyInt())).thenReturn((newOfficeRoom));

    mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(4))
            .andExpect(status().isOk());
  }

  @Test
  public void should_Find_All_Rooms() throws Exception {

    Mockito.when(officeRoomService.findAll())
            .thenReturn((List<OfficeRoom>) Stream.of(newOfficeRoom, newOfficeRoom1).collect(Collectors.toList()));

    mockMvc.perform(MockMvcRequestBuilders.get("/rooms/")).andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void when_Room_Is_Invalid_Then_Return_Exception400() throws Exception {

    newOfficeRoom.setName(null);

    String body = objectMapper.writeValueAsString(newOfficeRoom);

    mockMvc.perform(MockMvcRequestBuilders.post("/rooms/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());

  }

}
