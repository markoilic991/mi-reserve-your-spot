package com.pluralsight.reserve_your_spot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.Reservation;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.repository.ReservartionRepository;
import com.pluralsight.reserve_your_spot.service.ReservationService;
import com.pluralsight.reserve_your_spot.service.UserService;
import com.pluralsight.reserve_your_spot.service.WorkStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private ReservartionRepository reservartionRepository;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserService userService;

    @MockBean
    private WorkStationService workStationService;

    @MockBean
    private User user;

    @Test
    public void Should_Find_All_Reservations() throws Exception {

        String date = "2021-12-30";
        LocalDate parseDate = LocalDate.parse("2021-12-30");
        List<Reservation> reservations = new ArrayList<>();
        reservations
               .add(new Reservation
                       (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                                new WorkStation(1, "PD0001")));
        reservations
                .add(new Reservation
                        (2, parseDate, new User(2, "Miroslav Perovic", "miroslav.perovic@gmail.com"),
                                new WorkStation(4, "PD0004")));

        Mockito.when(reservationService.getAll()).thenReturn(reservations);

        mockMvc.perform(get("/reservations/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void Should_Find_Reservation_By_Id() throws Exception {

        String date = "2021-12-30";
        LocalDate parseDate = LocalDate.parse("2021-12-30");

        Reservation reservation = new Reservation
                (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                        new WorkStation(1, "PD0001"));

        reservation.setUser(new User(2, "Miroslav Perovic", "miroslav@gmail.com"));
        reservation.setWorkStation(new WorkStation(3, "PD0003"));

        Mockito.when(reservationService.getById((int) anyInt())).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void Should_Add_New_Reservation() throws Exception {

        String date = "2021-12-30";
        LocalDate parseDate = LocalDate.parse("2021-12-30");

        Reservation reservation = new Reservation
                (1, parseDate, new User(1, "Marko Ilic", "marko@gmail.com"),
                        new WorkStation(1, "PD0001"));

        reservation.setReservation_Id(1);
        reservation.setUser(new User(2, "Miroslav Perovic", "miroslav@gmail.com"));
        reservation.setWorkStation(new WorkStation(3, "PD0003"));

        Mockito.when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.post("/reservations/")
                        .content(objectMapper.writeValueAsString(reservation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation_Id").exists());

    }

    @Test
    public void when_User_Is_Invalid_Then_Reservation_Is_Invalid_Return_Status400() throws Exception {

        String date = "2021-12-30";
        LocalDate parseDate = LocalDate.parse("2021-12-30");
        Reservation reservation = new Reservation
                (1, parseDate, new User("", "saasdasdasdasdasd"),
                        new WorkStation(1, "PD0001"));

        String body = objectMapper.writeValueAsString(new User("", "saasdasdasdasdasd"));

        mockMvc.perform(MockMvcRequestBuilders.post("/reservations/").contentType("application/json").content(body))
                .andExpect(status().isBadRequest());
    }
}
