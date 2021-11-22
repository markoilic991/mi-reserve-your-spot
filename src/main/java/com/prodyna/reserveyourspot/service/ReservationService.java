package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.ListOfReservationsAlreadyExistException;
import com.prodyna.reserveyourspot.exception.ReservationAlreadyExistException;
import com.prodyna.reserveyourspot.exception.WorkStationBusyException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.repository.ReservartionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ReservationService {

  private ReservartionRepository reservartionRepository;

  @Autowired
  public ReservationService(ReservartionRepository reservartionRepository) {
    this.reservartionRepository = reservartionRepository;
  }

  public Reservation save(Reservation reservation) throws WorkStationBusyException {
    if (checkIfReservationExist(reservation)) {
      throw new ReservationAlreadyExistException("Reservation already exists in database!!!");
    } else {
      return reservartionRepository.save(reservation);
    }
  }

  public List<Reservation> saveAll(List<Reservation> revs) {
    if (checkIfReservationsExist(revs)) {
      throw new ListOfReservationsAlreadyExistException("Reservations already exist in database!!!");
    } else {
      return reservartionRepository.saveAll(revs);
    }
  }

  public List<Reservation> findAll() {
    List<Reservation> reservations = reservartionRepository.findAll();
    return reservations;
  }

  public Optional<Reservation> findById(int id) {
    return reservartionRepository.findById(id);
  }

  public String deleteById(int id) {
    reservartionRepository.deleteById((int) id);
    return "Reservations deleted!";
  }

  public Reservation findByDateAndWorkStationId(LocalDate dateFrom, LocalDate dateTo, int workStationId) {
    return reservartionRepository.findReservationByDateAndByWorkStationId(dateFrom, dateTo, workStationId);
  }

  public Reservation findByDateAndUserId(LocalDate dateFrom, LocalDate dateTo, int userId) {
    return reservartionRepository.findReservationByDateAndByUserId(dateFrom, dateTo, userId);
  }

  public List<Reservation> findAllReservations() {
    return reservartionRepository.findAllReservations();
  }

  public boolean checkIfReservationExist(Reservation reservation) {

    int userId = reservation.getUser().getId();
    LocalDate dateFrom = reservation.getDateFrom();
    LocalDate dateTo = reservation.getDateTo();
    int workStationId = reservation.getWorkStation().getId();
    boolean reservationExist = true;

    Optional<Reservation> newReservation1 = Optional.ofNullable(reservartionRepository.findReservationByDateAndByWorkStationId(dateFrom, dateTo, workStationId));
    Optional<Reservation> newReservation2 = Optional.ofNullable(reservartionRepository.findReservationByDateAndByUserId(dateFrom, dateTo, userId));

    if (newReservation1.isPresent()) {
      return reservationExist;
    } else if (newReservation2.isPresent()) {
      return reservationExist;
    }
    return false;
  }

  public boolean checkIfReservationsExist(List<Reservation> reservationList) {

    boolean reservationsExist = true;
    List<Reservation> reservations = reservartionRepository.findAllReservations();
    Reservation reservation = null;

    for (int i = 0; i < reservationList.size(); i++) {
      reservation = reservationList.get(i);
    }

    for (int i = 0; i < reservations.size(); i++) {
      Reservation temp = reservations.get(i);

      if (reservation.getDateFrom().equals(temp.getDateFrom()) &&
              reservation.getDateTo().equals(temp.getDateTo()) &&
              reservation.getWorkStation().getId() == temp.getWorkStation().getId()) {
        return reservationsExist;
      }
    }
    return false;
  }


}




