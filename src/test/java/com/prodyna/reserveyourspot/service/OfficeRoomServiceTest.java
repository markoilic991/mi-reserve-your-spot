package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(OfficeRoomService.class)
public class OfficeRoomServiceTest {

  @Autowired
  private OfficeRoomService officeRoomService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  @Autowired
  private OfficeRoomRepository officeRoomRepository;

  @MockBean
  private OfficeRoom officeRoom;

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
  public void should_Find_All_Rooms() {

    Mockito.when(officeRoomRepository.findAll())
            .thenReturn((List<OfficeRoom>) Stream.of(newOfficeRoom, newOfficeRoom1)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(2, officeRoomService.findAll().size());

  }

  @Test
  public void should_Find_Room_By_Id() {

    Mockito.when(officeRoomRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(newOfficeRoom));

    OfficeRoom officeRoom = officeRoomService.findById(1);

    Assertions.assertNotNull(officeRoom);
    Assertions.assertEquals("JAVA", officeRoom.getName());

  }

  @Test
  public void should_Delete_Room_By_Id() {

    officeRoomService.deleteById(newOfficeRoom.getId());

    Mockito.verify(officeRoomRepository, Mockito.times(1)).deleteById(newOfficeRoom.getId());

  }

}
