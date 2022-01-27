package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(ReservationService.class)
public class ReservationServiceTest {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  @Autowired
  private ReservationRepository reservationRepository;

  @MockBean
  private WorkStationRepository workStationRepository;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private Reservation reservation;

  Reservation reservationOne;
  Reservation reservationTwo;
  WorkStation workStationWindows;
  User userMarko;
  User userStefan;

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
  }

  @AfterEach
  public void cleanUp() {
    reservationRepository.deleteAll();
  }

  @Test
  public void should_Find_All_Reservations() {
    Mockito.when(reservationRepository.findAll())
            .thenReturn((List<Reservation>) Stream.of(reservationOne, reservationTwo)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(2, reservationService.findAll().size());
  }

  @Test
  public void should_Find_Reservation_By_Id() {
    Mockito.when(reservationRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(reservationOne));
    Reservation reservation = reservationService.findById(1);

    Assertions.assertNotNull(reservation);
    Assertions.assertEquals("Windows Work Station", reservation.getWorkStation().getDescription());
  }

  @Test
  public void should_Delete_Reservation() {
    reservationService.deleteById(reservationOne.getId());
    Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(reservationOne.getId());
  }

  @Test
  public void should_Find_Reservation_By_Date_And_Work_Station_Id() {
    reservationService.findByDateAndWorkStationId(reservationOne.getDate(), reservationOne.getWorkStation().getId());

    Mockito.verify(reservationRepository, Mockito.times(1))
            .findByDateAndWorkStationId(reservationOne.getDate(), reservationOne.getWorkStation().getId());
  }
}