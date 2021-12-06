package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
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
  public void should_Add_New_OfficeRoom() throws Exception {

    OfficeRoom officeRoomNew = new OfficeRoom();
    officeRoomNew.setId(1);
    officeRoomNew.setName("JAVA");
    officeRoomNew.setOrderNo(3);

    Mockito.when(officeRoomService.save(any(OfficeRoom.class))).thenReturn(officeRoomNew);

    mockMvc.perform(MockMvcRequestBuilders.post("/rooms/")
                    .content(objectMapper.writeValueAsString(officeRoomNew))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(3));
  }

  @Test
  public void should_Find_OfficeRoom_By_Id() throws Exception {

    OfficeRoom officeRoomNew = new OfficeRoom();
    officeRoomNew.setName("JAVA");
    officeRoomNew.setOrderNo(23);

    Mockito.when(officeRoomService.findById(anyInt())).thenReturn((officeRoomNew));

    mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("JAVA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.orderNo").value(23))
            .andExpect(status().isOk());
  }

  @Test
  public void should_Find_All_Rooms() throws Exception {

    List<OfficeRoom> roomsList = new ArrayList<>();
    roomsList.add(new OfficeRoom(1, "JAVA", 23));
    roomsList.add(new OfficeRoom(2, "QA", 17));
    roomsList.add(new OfficeRoom(3, ".NET", 10));

    Mockito.when(officeRoomService.findAll()).thenReturn(roomsList);

    mockMvc.perform(MockMvcRequestBuilders.get("/rooms/")).andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void when_Room_Is_Invalid_Then_Return_Exception400() throws Exception {

    OfficeRoom officeRoom = new OfficeRoom("", 7);

    String body = objectMapper.writeValueAsString(officeRoom);

    mockMvc.perform(MockMvcRequestBuilders.post("/rooms/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());

  }

}
