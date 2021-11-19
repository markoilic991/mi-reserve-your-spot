package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.service.ReservationService;
import com.prodyna.reserveyourspot.service.UserService;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  private UserService userService;
  private WorkStationService workStationService;
  private ReservationService reservationService;

  @Autowired
  public ReservationController(UserService userService, WorkStationService workStationService, ReservationService reservationService) {
    this.userService = userService;
    this.workStationService = workStationService;
    this.reservationService = reservationService;
  }

  @PostMapping("/")
  public Reservation save(@Valid @RequestBody Reservation reservation) {
    return reservationService.save(reservation);

  }

  @GetMapping("/")
  public List<Reservation> findAll() {
    List<Reservation> reservations = reservationService.findAll();
    return reservations;
  }

  @GetMapping("/{id}")
  public Optional<Reservation> findById(@PathVariable int id) {
    return reservationService.findById(id);
  }

  @PostMapping("/list")
  public List<Reservation> saveAll(@RequestBody List<Reservation> reservations) {
    return reservationService.saveAll(reservations);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    reservationService.deleteById(id);
  }


  @GetMapping("/reserve")
  public Reservation findByDateAndWorkStationId(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                @RequestParam(value = "workStationId") int workStationId) {
    return reservationService.findByDateAndWorkStationId(date, workStationId);
  }

  @GetMapping("/reserve1")
  public Reservation findByDateAndUserId(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                         @RequestParam(value = "userId") int userId) {
    return reservationService.findByDateAndUserId(date, userId);
  }

}



