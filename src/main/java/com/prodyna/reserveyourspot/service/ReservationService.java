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

  public List<Reservation> findAllReservationsByDateRange(LocalDate dateFrom, LocalDate dateTo) {
    return reservartionRepository.findAllReservationByDateRange(dateFrom, dateTo);
  }

  public Reservation findByDateAndWorkStationId(LocalDate date, int workStationId) {
    return reservartionRepository.findByDateAndWorkStationId(date, workStationId);
  }

  public Reservation findByDateAndUserId(LocalDate date, int userId) {
    return reservartionRepository.findByDateAndUserId(date, userId);
  }

  public boolean checkIfReservationExist(Reservation reservation) {

    int userId = reservation.getUser().getId();
    LocalDate date = reservation.getDate();
    int workStationId = reservation.getWorkStation().getId();
    boolean reservationExist = true;

    Optional<Reservation> newReservation1 = Optional.ofNullable(reservartionRepository.findByDateAndWorkStationId(date, workStationId));
    Optional<Reservation> newReservation2 = Optional.ofNullable(reservartionRepository.findByDateAndUserId(date, userId));

    if (newReservation1.isPresent()) {
      return reservationExist;
    } else if (newReservation2.isPresent()) {
      return reservationExist;
    }
    return false;
  }

  public boolean checkIfReservationsExist(List<Reservation> reservations) {

    boolean reservationsExist = true;
    Optional<Reservation> reservation = null;

    for (int i = 0; i < reservations.size(); i++) {
      reservation = Optional.ofNullable(reservations.get(i));
    }

    reservation = Optional.ofNullable(reservartionRepository.findByDateAndWorkStationId(reservation.get().getDate(), reservation.get().getWorkStation().getId()));

    if (reservation.isPresent()) {
      return reservationsExist;
    }
    return false;
  }

}




