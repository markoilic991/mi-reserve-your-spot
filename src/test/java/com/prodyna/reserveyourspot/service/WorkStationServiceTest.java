package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
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

@WebMvcTest(WorkStationService.class)
public class WorkStationServiceTest {

  @Autowired
  private WorkStationService workStationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WorkStationRepository workStationRepository;

  @MockBean
  private OfficeRoomRepository officeRoomRepository;

  @MockBean
  private WorkStation workStation;

  WorkStation workStationWindows;
  WorkStation workStationLinux;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    workStationWindows = new WorkStation();
    workStationWindows.setId(1);
    workStationWindows.setCode("PD00002");
    workStationWindows.setDescription("Windows WorkStation");
    workStationLinux = new WorkStation();
    workStationLinux.setId(2);
    workStationLinux.setCode("PD11145");
    workStationLinux.setDescription("Linux WorkStation");
  }

  @AfterEach
  public void cleanUp() {
    workStationRepository.deleteAll();
  }

  @Test
  public void should_Get_All_Stations() {
    Mockito.when(workStationRepository.findAll())
            .thenReturn((List<WorkStation>) Stream.of(workStationWindows, workStationLinux)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(2, workStationService.findAll().size());
  }

  @Test
  public void should_Get_Station_By_Id() {
    Mockito.when(workStationRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(workStationLinux));
    WorkStation workStation = workStationService.findById(1);

    Assertions.assertNotNull(workStation);
    Assertions.assertEquals("Linux WorkStation", workStationLinux.getDescription());
  }

  @Test
  public void should_Delete_Station() {
    workStationService.deleteById(workStationWindows.getId());

    Mockito.verify(workStationRepository, Mockito.times(1)).deleteById(workStationWindows.getId());
  }
}
