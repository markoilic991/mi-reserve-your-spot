package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.service.ReservationService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

  @Autowired
  private ReservationController reservationController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private Reservation reservation;

  @MockBean
  private ReservationRepository reservationRepository;

  @MockBean
  private ReservationService reservationService;

  @MockBean
  private WorkStationService workStationService;

  Reservation reservationOne;
  Reservation reservationTwo;
  WorkStation workStationWindows;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    workStationWindows = new WorkStation();
    workStationWindows.setId(1);
    workStationWindows.setCode("PD76332");
    workStationWindows.setDescription("Windows Work Station");
    reservationOne = new Reservation();
    reservationOne.setId(1);
    String dateOne = "2021-12-30";
    LocalDate parseDateOne = LocalDate.parse(dateOne);
    reservationOne.setDate(parseDateOne);
    reservationOne.setWorkStation(workStationWindows);
    reservationTwo = new Reservation();
    reservationTwo.setId(2);
    String dateTwo = "2022-01-20";
    LocalDate parseDateTwo = LocalDate.parse(dateTwo);
    reservationTwo.setDate(parseDateTwo);
    reservationTwo.setWorkStation(workStationWindows);
  }

  @AfterEach
  public void cleanUp() {
    reservationRepository.deleteAll();
  }

  @Test
  public void should_Find_All_Reservations() throws Exception {
    Mockito.when(reservationService.findAll())
            .thenReturn((List<Reservation>) Stream.of(reservationOne, reservationTwo).collect(Collectors.toList()));

    mockMvc.perform(get("/api/reservations")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void should_Find_Reservation_By_Id() throws Exception {
    Mockito.when(reservationService.findById((int) anyInt())).thenReturn((reservationOne));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/1"))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void should_Add_New_Reservation() throws Exception {
    Mockito.when(reservationService.save(any(Reservation.class))).thenReturn(reservationOne);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations/reservation/1")
                    .content(objectMapper.writeValueAsString(reservationOne))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
  }
}
