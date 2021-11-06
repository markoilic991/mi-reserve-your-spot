package com.pluralsight.reserve_your_spot.controller;

import com.pluralsight.reserve_your_spot.model.Reservation;
import com.pluralsight.reserve_your_spot.service.ReservationService;
import com.pluralsight.reserve_your_spot.service.UserService;
import com.pluralsight.reserve_your_spot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private UserService userService;
    private WorkStationService workStationService;

    private ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/")
    public Reservation addReservation(@Valid @RequestBody Reservation reservation){
        return reservationService.addReservation(reservation);
    }

    @GetMapping("/")
    public List<Reservation> getAll(){
        List<Reservation>reservations = reservationService.getAll();
        return reservations;
    }
}
