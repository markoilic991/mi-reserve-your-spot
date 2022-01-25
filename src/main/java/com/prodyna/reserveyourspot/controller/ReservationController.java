package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  @PostMapping("/reservation/{userId}/{workStationId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Reservation saveReservation(@PathVariable("userId") int userId, @PathVariable("workStationId") int workStationId, @RequestBody Reservation reservation) {
    return reservationService.saveReservation(userId, workStationId, reservation);
  }

  @PostMapping("/save")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Reservation> saveReservations(@RequestParam(value = "userId") int userId,
                                            @RequestParam(value = "workStationId") int workStationId,
                                            @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                            @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
    return reservationService.saveReservations(userId, workStationId, dateFrom, dateTo);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteById(@PathVariable int id) {
    reservationService.deleteById(id);
    return "Reservation deleted successfully!";
  }

  @DeleteMapping("/cancel")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String cancelReservation(@RequestParam(value = "userId") int userId,
                                  @RequestParam(value = "workStationId") int workStationId,
                                  @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    reservationService.cancelReservation(userId, workStationId, date);
    return "Reservation cancelled successfully!";
  }

  @PutMapping("/{id}")
  public Reservation update(@RequestBody Reservation reservation, @PathVariable int id) {
    return reservationService.updateReservation(reservation, id);
  }
}
