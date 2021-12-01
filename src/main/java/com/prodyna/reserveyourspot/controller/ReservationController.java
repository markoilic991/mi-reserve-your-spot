package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

  @GetMapping("/dateRange")
  public List<Reservation> findAllByDateRange(@RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                              @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
    return reservationService.findAllReservationsByDateRange(dateFrom, dateTo);
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
  public Reservation findByDateAndWorkStationId(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                @RequestParam(value = "workStationId") int workStationId) {
    return reservationService.findByDateAndWorkStationId(date, workStationId);
  }

  @GetMapping("/dateAndUser")
  public Reservation findByDateAndUserId(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                         @RequestParam(value = "userId") int userId) {
    return reservationService.findByDateAndUserId(date, userId);
  }

  /*
  @PostMapping("/saveReservations")
  public List<Reservation> saveAllRevs(@RequestParam(value = "userId") int userId,
                                       @RequestParam(value = "workStationId") int workStationId,
                                       @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                       @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo){
    return  reservationService.saveAllRevs(userId, workStationId, dateFrom, dateTo);
  }

   */

}



