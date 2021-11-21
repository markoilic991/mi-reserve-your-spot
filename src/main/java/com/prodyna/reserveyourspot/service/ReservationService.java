package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.ReservationAlreadyExistException;
import com.prodyna.reserveyourspot.exception.WorkStationBusyException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.repository.ReservartionRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
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
  private UserRepository userRepository;
  private WorkStationRepository workStationRepository;

  @Autowired
  public ReservationService(ReservartionRepository reservartionRepository, UserRepository userRepository, WorkStationRepository workStationRepository) {
    this.reservartionRepository = reservartionRepository;
    this.userRepository = userRepository;
    this.workStationRepository = workStationRepository;
  }

  public Reservation save(Reservation reservation) throws WorkStationBusyException {
    if (checkIfReservationExist(reservation)) {
      throw new ReservationAlreadyExistException("Reservation already exists in database!!!");
    } else {
      return reservartionRepository.save(reservation);
    }
  }

  public List<Reservation> saveAll(List<Reservation> revs) {
    return reservartionRepository.saveAll(revs);
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

  //query
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

}




