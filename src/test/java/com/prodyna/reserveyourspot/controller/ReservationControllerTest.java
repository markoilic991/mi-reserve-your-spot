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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
  private UserService userService;

  @MockBean
  private WorkStationService workStationService;

  @MockBean
  private User user;

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
  public void should_Find_All_Reservations() throws Exception {

    Mockito.when(reservationService.findAll())
            .thenReturn((List<Reservation>) Stream.of(newReservation, newReservation1).collect(Collectors.toList()));

    mockMvc.perform(get("/reservations/")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void should_Find_Reservation_By_Id() throws Exception {

    Mockito.when(reservationService.findById((int) anyInt())).thenReturn((newReservation));

    mockMvc.perform(MockMvcRequestBuilders.get("/reservations/1"))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void should_Add_New_Reservation() throws Exception {

    Mockito.when(reservationService.save(any(Reservation.class))).thenReturn(newReservation);

    mockMvc.perform(MockMvcRequestBuilders.post("/reservations/")
                    .content(objectMapper.writeValueAsString(newReservation))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

  }

  @Test
  public void when_User_Is_Invalid_Then_Reservation_Is_Invalid_Return_Status400() throws Exception {

    newReservation.getUser().setEmail("asdasdafrfsadsdfasda");

    String body = objectMapper.writeValueAsString(newReservation.getUser());

    mockMvc.perform(MockMvcRequestBuilders.post("/reservations/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());
  }

}
