package com.myproject.ReserveYourSpot.controller;

import com.myproject.ReserveYourSpot.service.ReservationService;
import com.myproject.ReserveYourSpot.model.Reservation;
import com.myproject.ReserveYourSpot.service.UserService;
import com.myproject.ReserveYourSpot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private UserService userService;
    private WorkStationService workStationService;
    private ReservationService reservationService;

    @Autowired
    // Advice: very good (autowiring with constructor), this can be covered with lombok annotations like @AllArgsConstructor
    public ReservationController(UserService userService, WorkStationService workStationService, ReservationService reservationService) {
        this.userService = userService;
        this.workStationService = workStationService;
        this.reservationService = reservationService;
    }

    @PostMapping("/")
    public Reservation addReservation(@Valid @RequestBody Reservation reservation) {
            return reservationService.addReservation(reservation);

    }

    @GetMapping("/")
    // Comment: naming
    public List<Reservation> getAll(){
        List<Reservation>reservations = reservationService.getAll();
        return reservations;
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable int id){
        return reservationService.getById(id);
    }

    @PostMapping("/list")
    // Comment: naming
    public List<Reservation> listOfReservations(@RequestBody List<Reservation> reservations){
        return reservationService.saveAll(reservations);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable int id){
        reservationService.deleteById(id);
    }


    }



