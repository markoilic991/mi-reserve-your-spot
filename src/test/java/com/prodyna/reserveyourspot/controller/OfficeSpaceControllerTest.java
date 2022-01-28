package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
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
@WebMvcTest(OfficeSpaceController.class)
public class OfficeSpaceControllerTest {

  @Autowired
  private OfficeSpaceController officeSpaceController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  @Autowired
  private OfficeSpaceService officeSpaceService;

  @MockBean
  private OfficeRoomService officeRoomService;

  @MockBean
  @Autowired
  private OfficeSpaceRepository officeSpaceRepository;

  @MockBean
  private OfficeSpace officeSpace;

  @MockBean
  private OfficeRoom officeRoom;

  OfficeSpace officeSpaceProdyna;
  OfficeRoom officeRoomJava;
  OfficeRoom officeRoomDotNet;
  Set<OfficeRoom> officeRooms;
  OfficeSpace officeSpaceQnit;
  OfficeSpace officeSpaceUpdated;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    officeRoomJava = new OfficeRoom();
    officeRoomJava.setId(1);
    officeRoomJava.setName("JAVA");
    officeRoomJava.setCode(4);
    officeRoomDotNet = new OfficeRoom();
    officeRoomDotNet.setId(2);
    officeRoomDotNet.setName(".NET");
    officeRoomDotNet.setCode(3);
    officeRooms = new HashSet<>();
    officeRooms.add(officeRoomJava);
    officeRooms.add(officeRoomDotNet);
    officeSpaceProdyna = new OfficeSpace();
    officeSpaceProdyna.setId(1);
    officeSpaceProdyna.setName("PRODYNA");
    officeSpaceProdyna.setDescription("Business garden");
    officeSpaceProdyna.setRooms(officeRooms);
    officeSpaceQnit = new OfficeSpace();
    officeSpaceQnit.setName("Qnit");
    officeSpaceQnit.setDescription("Qnit doo");
  }

  @AfterEach
  public void cleanUp() {
    officeSpaceRepository.deleteAll();
  }

  @Test
  public void should_Add_New_OfficeSpace() throws Exception {
    Mockito.when(officeSpaceService.save(any(OfficeSpace.class))).thenReturn(officeSpaceQnit);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/office-spaces")
                    .content(objectMapper.writeValueAsString(officeSpaceQnit))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
  }

  @Test
  public void should_Find_OfficeSpace_By_Id() throws Exception {
    Mockito.when(officeSpaceService.findOfficeSpaceById(anyInt())).thenReturn((officeSpaceProdyna));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/office-spaces/1"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PRODYNA"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Business garden"))
            .andExpect(status().isOk());
  }

  @Test
  public void should_Find_All_Office_Spaces() throws Exception {
    Mockito.when(officeSpaceService.findAll())
            .thenReturn((List<OfficeSpace>) Stream.of(officeSpaceProdyna).collect(Collectors.toList()));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/office-spaces")).andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void should_Update_Office_Space() {
    officeSpaceUpdated = new OfficeSpace();
    officeSpaceUpdated.setName("Qnit Wien");
    officeSpaceUpdated.setDescription(officeSpaceProdyna.getDescription());

    Mockito.when(officeSpaceService.findOfficeSpaceById(officeSpaceProdyna.getId())).thenReturn(officeSpaceUpdated);
  }

  @Test
  public void should_Delete_Office_Space() throws Exception {
    Mockito.when(officeSpaceService.findOfficeSpaceById(anyInt())).thenReturn(officeSpaceProdyna);
    Mockito.when(officeSpaceService.deleteById(officeSpaceProdyna.getId())).thenReturn("Success");
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/office-spaces/1")).andExpect(status().isNoContent());
  }
}