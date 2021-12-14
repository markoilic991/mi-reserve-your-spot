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
  private UserRepository userRepository;

  @MockBean
  private WorkStationRepository workStationRepository;

  @MockBean
  private Reservation reservation;

  Reservation newReservation;
  Reservation newReservation1;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    User user = new User(1, "Marko Ilic", "marko.ilic@prodyna.com");
    WorkStation workStation = new WorkStation(1, "PD0001", "Mac");

    newReservation = new Reservation();
    newReservation.setId(1);
    String date = "2021-12-30";
    LocalDate parseDate = LocalDate.parse(date);
    newReservation.setDate(parseDate);
    newReservation.setUser(user);
    newReservation.setWorkStation(workStation);
    newReservation1 = new Reservation();
    newReservation1.setId(2);
    String date1 = "2022-12-30";
    LocalDate parseDate1 = LocalDate.parse(date1);
    newReservation1.setDate(parseDate1);
    newReservation1.setUser(user);
    newReservation1.setWorkStation(workStation);

  }

  @AfterEach
  public void cleanUp() {

    reservationRepository.findAll();
    reservationRepository.deleteAll();

  }

  @Test
  public void should_Find_All_Reservations() {

    Mockito.when(reservationRepository.findAll())
            .thenReturn((List<Reservation>) Stream.of(newReservation, newReservation1)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(2, reservationService.findAll().size());

  }

  @Test
  public void should_Find_Reservation_By_Id() {

    Mockito.when(reservationRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(newReservation));

    Reservation reservation = reservationService.findById(1);

    Assertions.assertNotNull(reservation);
    Assertions.assertEquals("Marko Ilic", reservation.getUser().getName());

  }

  @Test
  public void should_Delete_Reservation() {

    reservationService.deleteById(newReservation1.getId());

    Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(newReservation1.getId());

  }

  @Test
  public void should_Find_Reservation_By_Date_And_Work_Station_Id() {

    reservationService.findByDateAndWorkStationId(newReservation1.getDate(), newReservation1.getWorkStation().getId());

    Mockito.verify(reservationRepository, Mockito.times(1))
            .findByDateAndWorkStationId(newReservation1.getDate(), newReservation1.getWorkStation().getId());

  }

  @Test
  public void should_Cancel_Reservation_By_Date_And_User_Id_And_WorkStation_Id() {

    reservationService.cancelReservation(newReservation.getUser().getId(), newReservation.getWorkStation().getId(), newReservation.getDate());

    Mockito.verify(reservationRepository, Mockito.times(1))
            .deleteByDateAndUserIdAndWorkStationId(newReservation.getDate(), newReservation.getUser().getId(), newReservation.getWorkStation().getId());

  }
}
