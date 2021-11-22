package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.service.ReservationService;
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

  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
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


  @GetMapping("/dateAndWorkStation")
  public Reservation findByDateAndWorkStationId(@RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                @RequestParam(value = "workStationId") int workStationId) {
    return reservationService.findByDateAndWorkStationId(dateFrom, dateTo, workStationId);
  }

  @GetMapping("/dateAndUser")
  public Reservation findByDateAndUserId(@RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                         @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                         @RequestParam(value = "userId") int userId) {
    return reservationService.findByDateAndUserId(dateFrom, dateTo, userId);
  }

}



