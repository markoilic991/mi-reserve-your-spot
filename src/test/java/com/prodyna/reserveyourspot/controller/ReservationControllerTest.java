package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.service.ReservationService;
import com.prodyna.reserveyourspot.service.UserService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  @Autowired
  private ReservationService reservationService;

  @MockBean
  private WorkStationService workStationService;

  @MockBean
  private UserService userService;

  Reservation reservationOne;
  Reservation reservationTwo;
  WorkStation workStationWindows;
  Reservation reservationThree;
  User userMarko;
  User userStefan;
  Reservation reservationUpdated;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    workStationWindows = new WorkStation();
    workStationWindows.setId(1);
    workStationWindows.setCode("PD76332");
    workStationWindows.setDescription("Windows Work Station");
    userMarko = new User();
    userMarko.setId(1);
    userMarko.setName("Marko Ilic");
    userMarko.setEmail("marko.ilic@prodyna.com");
    userStefan = new User();
    userStefan.setId(2);
    userStefan.setName("Stefan Cvijic");
    userStefan.setEmail("stefan.cvijic@gmail.com");
    reservationOne = new Reservation();
    reservationOne.setId(1);
    String dateOne = "2021-12-30";
    LocalDate parseDateOne = LocalDate.parse(dateOne);
    reservationOne.setDate(parseDateOne);
    reservationOne.setUser(userMarko);
    reservationOne.setWorkStation(workStationWindows);
    reservationTwo = new Reservation();
    reservationTwo.setId(2);
    String dateTwo = "2022-01-20";
    LocalDate parseDateTwo = LocalDate.parse(dateTwo);
    reservationTwo.setDate(parseDateTwo);
    reservationTwo.setUser(userStefan);
    reservationTwo.setWorkStation(workStationWindows);
    reservationThree = new Reservation();
    String dateThree = "2022-01-22";
    LocalDate parseDateThree = LocalDate.parse(dateThree);
    reservationThree.setDate(parseDateThree);
    reservationThree.setUser(userStefan);
    reservationThree.setWorkStation(workStationWindows);
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
  public void should_Update_Reservation() {
    reservationUpdated = new Reservation();
    String dateNew = "2022-01-22";
    LocalDate parseDateNew = LocalDate.parse(dateNew);
    reservationUpdated.setDate(parseDateNew);
    reservationUpdated.setWorkStation(reservationOne.getWorkStation());
    reservationUpdated.setUser(reservationOne.getUser());

    Mockito.when(reservationService.findById(reservationOne.getId())).thenReturn(reservationUpdated);
  }

  @Test
  public void should_Delete_Reservation() throws Exception {
    Mockito.when(reservationService.deleteById(reservationOne.getId())).thenReturn("Success");
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservations/1")).andExpect(status().isNoContent());
  }
}
