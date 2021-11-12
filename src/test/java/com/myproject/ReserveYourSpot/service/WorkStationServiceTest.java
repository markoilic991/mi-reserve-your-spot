package com.myproject.ReserveYourSpot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ReserveYourSpot.model.WorkStation;
import com.myproject.ReserveYourSpot.repository.WorkStationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private WorkStation workStation;

    @Test
    public void Should_Get_All_Stations(){

        WorkStation workStation1 = new WorkStation(1, "PD441100");
        WorkStation workStation2 = new WorkStation(2, "PD447711");
        WorkStation workStation3 = new WorkStation(3, "PD445566");

        Mockito.when(workStationRepository.findAll())
                .thenReturn((List<WorkStation>) Stream.of(workStation1, workStation2, workStation3)
                        .collect(Collectors.toList()));

        Assertions.assertEquals(3, workStationService.getAll().size());
    }

    @Test
    public void Should_Get_Station_By_Id(){

        WorkStation workStation1 = new WorkStation(1, "PD441100");
        workStationService.getById(workStation1.getId());

        Mockito.verify(workStationRepository, Mockito.times(1)).findById(workStation1.getId());
    }

    @Test
    public void Should_Delete_Station(){

        WorkStation workStation1 = new WorkStation(1, "PD441100");
        workStationService.deleteById(workStation1.getId());

        Mockito.verify(workStationRepository, Mockito.times(1)).deleteById(workStation1.getId());
    }
}