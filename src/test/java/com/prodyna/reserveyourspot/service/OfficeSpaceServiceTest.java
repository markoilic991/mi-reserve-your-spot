package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(OfficeSpaceService.class)
public class OfficeSpaceServiceTest {

  @Autowired
  private OfficeSpaceService officeSpaceService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  @Autowired
  private OfficeSpaceRepository officeSpaceRepository;

  @MockBean
  private OfficeSpace officeSpace;

  OfficeSpace officeSpaceProdyna;
  OfficeRoom officeRoomJava;
  OfficeRoom officeRoomDotNet;
  List<OfficeRoom> officeRooms;

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
    officeRooms = new ArrayList<>();
    officeRooms.add(officeRoomJava);
    officeRooms.add(officeRoomDotNet);
    officeSpaceProdyna = new OfficeSpace();
    officeSpaceProdyna.setId(1);
    officeSpaceProdyna.setName("PRODYNA");
    officeSpaceProdyna.setDescription("Business garden");
    officeSpaceProdyna.setRooms(officeRooms);

  }

  @AfterEach
  public void cleanUp() {
    officeSpaceRepository.deleteAll();
  }

  @Test
  public void should_Find_All_Office_Spaces() {
    Mockito.when(officeSpaceRepository.findAll())
            .thenReturn((List<OfficeSpace>) Stream.of(officeSpaceProdyna)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(1, officeSpaceService.findAll().size());
  }

  @Test
  public void should_Find_Office_Space_By_Id() {
    Mockito.when(officeSpaceRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(officeSpaceProdyna));
    OfficeSpace officeSpace = officeSpaceService.findOfficeSpaceById(1);

    Assertions.assertNotNull(officeSpace);
    Assertions.assertEquals("PRODYNA", officeSpace.getName());
  }

  @Test
  public void should_Delete_Office_Space_By_Id() {
    officeSpaceService.deleteById(officeSpaceProdyna.getId());

    Mockito.verify(officeSpaceRepository, Mockito.times(1)).deleteById(officeSpaceProdyna.getId());
  }
}
