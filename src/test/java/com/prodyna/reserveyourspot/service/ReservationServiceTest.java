package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservartionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebMvcTest(ReservationService.class)
public class ReservationServiceTest {

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReservartionRepository reservartionRepository;

  @MockBean
  private Reservation reservation;

  @Test
  public void should_Find_All_Reservations() {

    String date = "2021-12-30";
    LocalDate parseDate = LocalDate.parse(date);

    Reservation reservation1 = new Reservation
            (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                    new WorkStation(1, "PD0001", "Mac"));
    Reservation reservation2 = new Reservation
            (2, parseDate, new User(2, "Milos Mikic", "milos@gmail.com"),
                    new WorkStation(3, "PD0003", "Mac"));

    Mockito.when(reservartionRepository.findAll())
            .thenReturn((List<Reservation>) Stream.of(reservation1, reservation2)
                    .collect(Collectors.toList()));

    Assertions.assertEquals(2, reservationService.findAll().size());

  }

  @Test
  public void should_Find_Reservation_By_Id() {

    String date = "2021-12-30";
    LocalDate parseDate = LocalDate.parse(date);


    Reservation reservation1 = new Reservation
            (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                    new WorkStation(1, "PD0001", "Linux"));

    reservationService.findById(reservation1.getId());

    Mockito.verify(reservartionRepository, Mockito.times(1)).findById(reservation1.getId());
  }

  @Test
  public void should_Delete_Reservation() {

    String date = "2021-12-30";
    LocalDate parseDate = LocalDate.parse(date);

    Reservation reservation1 = new Reservation
            (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                    new WorkStation(1, "PD0001", "Mac"));

    reservationService.deleteById(reservation1.getId());

    Mockito.verify(reservartionRepository, Mockito.times(1)).deleteById(reservation1.getId());
  }

  @Test
  public void should_Find_Reservation_By_Date_And_Work_Station_Id() {

    String date = "2021-12-30";
    LocalDate parseDate = LocalDate.parse(date);

    Reservation reservation1 = new Reservation
            (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                    new WorkStation(1, "PD0001", "Linux"));

    reservationService.findByDateAndWorkStationId(reservation1.getDate(), reservation1.getWorkStation().getId());

    Mockito.verify(reservartionRepository, Mockito.times(1))
            .findByDateAndWorkStationId(reservation1.getDate(), reservation1.getWorkStation().getId());

  }
}
