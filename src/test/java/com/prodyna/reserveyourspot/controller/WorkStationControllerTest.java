package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

  WorkStation workStationWindows;
  WorkStation workStationLinux;
  WorkStation workStationMac;
  WorkStation workStationUpdated;

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
    workStationMac = new WorkStation();
    workStationMac.setCode("PD00000");
    workStationMac.setDescription("Mac WorkStation");
  }

  @AfterEach
  public void cleanUp() {
    workStationRepository.deleteAll();
  }

  @Test
  public void should_Update_Work_Station() {
    workStationUpdated = new WorkStation();
    workStationUpdated.setDescription("Mac Working Station");
    workStationUpdated.setCode(workStationLinux.getCode());

    Mockito.when(workStationService.findById(workStationLinux.getId())).thenReturn(workStationUpdated);
  }

  @Test
  public void should_Delete_WorkStation() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/work-stations/2")).andExpect(status().isNoContent());
  }
}
