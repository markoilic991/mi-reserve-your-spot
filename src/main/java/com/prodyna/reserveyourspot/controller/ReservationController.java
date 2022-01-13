package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  public List<Reservation> findAll() {
    return reservationService.findAll();
  }

  @GetMapping("/dateRange")
  public List<Reservation> findAllByDateRange(@RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                              @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
    return reservationService.findAllReservationsByDateRange(dateFrom, dateTo);
  }

  @GetMapping("/{id}")
  public Reservation findById(@PathVariable int id) {
    return reservationService.findById(id);
  }

  @GetMapping("/dateAndWorkStation")
  public Reservation findByDateAndWorkStationId(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                @RequestParam(value = "workStationId") int workStationId) {
    return reservationService.findByDateAndWorkStationId(date, workStationId);
  }

  @PostMapping("/reservation/{workStationId}")
  public Reservation saveReservation(@PathVariable("workStationId") int workStationId, @RequestBody Reservation reservation) {
    return reservationService.saveReservation(workStationId, reservation);
  }

  @PostMapping("/save")
  public List<Reservation> saveReservations(@RequestParam(value = "workStationId") int workStationId,
                                            @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                            @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
    return reservationService.saveReservations(workStationId, dateFrom, dateTo);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    reservationService.deleteById(id);
  }

  @DeleteMapping("/cancel")
  public void cancelReservation(@RequestParam(value = "workStationId") int workStationId,
                                @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    reservationService.cancelReservation(workStationId, date);
  }

  @PutMapping("/{id}")
  public Reservation update(@RequestBody Reservation reservation, @PathVariable int id) {
    return reservationService.updateReservation(reservation, id);
  }
}
