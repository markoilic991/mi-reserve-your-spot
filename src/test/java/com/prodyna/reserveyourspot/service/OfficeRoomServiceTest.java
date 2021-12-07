package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebMvcTest(OfficeRoomService.class)
public class OfficeRoomServiceTest {

  @Autowired
  private OfficeRoomService officeRoomService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OfficeRoomRepository officeRoomRepository;

  @MockBean
  private OfficeRoom officeRoom;


  @Test
  public void should_Find_All_Rooms() {

    OfficeRoom officeRoom1 = new OfficeRoom(1, "JAVA", 2);
    OfficeRoom officeRoom2 = new OfficeRoom(2, "QA", 3);
    OfficeRoom officeRoom3 = new OfficeRoom(3, ".NET", 4);

    Mockito.when(officeRoomRepository.findAll())
            .thenReturn((List<OfficeRoom>) Stream.of(officeRoom1, officeRoom2, officeRoom3)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(3, officeRoomService.findAll().size());

  }

  @Test
  public void should_Find_Room_By_Id() {

    Optional<OfficeRoom> officeRoom = Optional.of(new OfficeRoom(1, "JAVA", 2));
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(officeRoom.get().getId());

    if (optionalOfficeRoom.isPresent()) {
      optionalOfficeRoom.get();
    }

    Mockito.verify(officeRoomRepository, Mockito.times(1)).findById(officeRoom.get().getId());

  }

  @Test
  public void should_Delete_Room_By_Id() {

    OfficeRoom officeRoom = new OfficeRoom(1, "JAVA", 3);
    officeRoomService.deleteById(officeRoom.getId());

    Mockito.verify(officeRoomRepository, Mockito.times(1)).deleteById(officeRoom.getId());
  }
}
