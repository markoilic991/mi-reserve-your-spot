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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @GetMapping("/{id}")
  public Reservation findById(@PathVariable int id) {
    return reservationService.findById(id);
  }

  @GetMapping
  public List<Reservation> findAllReservationsByUserIdAndDateRange(@RequestParam(value = "userId") int userId,
                                                                   @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                                   @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
    return reservationService.findAllReservationsByUserIdAndDateRange(userId, dateFrom, dateTo);
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

  @DeleteMapping("/deleteAll")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAllReservations(@RequestParam(value = "listIDs") List<Integer> reservationsIDs){
      reservationService.deleteAll(reservationsIDs);
  }

  @DeleteMapping("/cancel")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String cancelReservation(@RequestParam(value = "userId") int userId,
                                  @RequestParam(value = "workStationId") int workStationId,
                                  @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    reservationService.cancelReservation(userId, workStationId, date);
    return "Reservation cancelled successfully!";
  }
}
