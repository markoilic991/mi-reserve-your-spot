package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.ReservationAlreadyExistException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

  private ReservationRepository reservationRepository;
  private WorkStationRepository workStationRepository;
  private UserRepository userRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository,
                            WorkStationRepository workStationRepository,
                            UserRepository userRepository) {
    this.reservationRepository = reservationRepository;
    this.workStationRepository = workStationRepository;
    this.userRepository = userRepository;
  }

  public List<Reservation> findAll() {
    return reservationRepository.findAll();
  }

  public Reservation findById(int id) {
    Optional<Reservation> optionalReservation = reservationRepository.findById(id);
    if (optionalReservation.isPresent()) {
      return optionalReservation.get();
    }
    throw new EntityNotFoundException("Reservation with id: " + " does not exist!");
  }

  public List<Reservation> findAllReservationsByDateRange(LocalDate dateFrom, LocalDate dateTo) {
    return reservationRepository.findAllReservationByDateRange(dateFrom, dateTo);
  }

  public Reservation findByDateAndWorkStationId(LocalDate date, int workStationId) {
    return reservationRepository.findByDateAndWorkStationId(date, workStationId);
  }

  public Reservation save(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  public Reservation saveReservation(int userId, int workStationId, Reservation reservation) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(workStationId);
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalWorkStation.isPresent() || !optionalUser.isPresent()) {
      throw new EntityNotFoundException("User or workStation do not exist in database! Reservation can not be saved!");
    }
    WorkStation workStationToSave = optionalWorkStation.get();
    User userToSave = optionalUser.get();
    reservation.setWorkStation(workStationToSave);
    reservation.setUser(userToSave);
    if (checkIfReservationExist(reservation)) {
      throw new ReservationAlreadyExistException("Reservation already exists in database!!!");
    } else {
      return reservationRepository.save(reservation);
    }
  }

  public List<Reservation> saveReservations(int userId, int workStationId, LocalDate from, LocalDate to) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(workStationId);
    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalWorkStation.isPresent() && optionalUser.isPresent()) {
      List<Reservation> reservations = dateRangeFromTo(from, to).stream()
              .map(date -> new Reservation(date, optionalUser.get(), optionalWorkStation.get()))
              .collect(Collectors.toList());

      if (checkIfReservationsExist(from, to)) {
        throw new ReservationAlreadyExistException("Reservations by this date range already exist in database. Choose another data range!");
      }
      return reservationRepository.saveAll(reservations);
    }
    throw new EntityNotFoundException("Some of input data do not exist in database, check again!");
  }

  public String deleteById(int id) {
    Optional<Reservation> optionalReservation = reservationRepository.findById(id);
    if (!optionalReservation.isPresent()) {
      throw new EntityNotFoundException("Reservation with id " + id + " does not exist in database!");
    }
    reservationRepository.deleteById(id);
    return "Reservations deleted!";
  }

  public String cancelReservation(int userId, int workStationId, LocalDate date) {
    Optional<Reservation> optionalReservation =
            Optional.ofNullable(reservationRepository.findByDateAndUserIdAndWorkStationId(date, userId, workStationId));
    if (!optionalReservation.isPresent()) {
      throw new EntityNotFoundException("Reservation has been already cancelled!");
    }
    reservationRepository.deleteByDateAndUserIdAndWorkStationId(date, userId, workStationId);
    return "Reservation cancelled successfully!";
  }

  public Reservation updateReservation(Reservation reservation, int id) {
    Optional<Reservation> optionalReservation = reservationRepository.findById(id);
    if (!optionalReservation.isPresent()) {
      throw new EntityNotFoundException("Reservation does not exist in database!");
    }
    Reservation reservationUpdated = optionalReservation.get();
    reservationUpdated.setDate(reservation.getDate());
    return reservationRepository.save(reservationUpdated);
  }

  public boolean checkIfReservationExist(Reservation reservation) {
    LocalDate date = reservation.getDate();
    int userId = reservation.getUser().getId();
    int workStationId = reservation.getWorkStation().getId();
    boolean reservationExist = true;

    Optional<Reservation> newReservation1 = Optional.ofNullable(reservationRepository.findByDateAndWorkStationId(date, workStationId));
    Optional<Reservation> newReservation2 = Optional.ofNullable(reservationRepository.findByDateAndUserId(date, userId));

    if (newReservation1.isPresent()) {
      return reservationExist;
    } else if (newReservation2.isPresent()) {
      return reservationExist;
    }
    return false;
  }

  public boolean checkIfReservationsExist(LocalDate dateFrom, LocalDate dateTo) {
    List<Reservation> reservationList = reservationRepository.findAllReservationByDateRange(dateFrom, dateTo);
    Reservation reservation = null;
    boolean reservationsExist = true;

    for (int i = 0; i < reservationList.size(); i++) {
      reservation = reservationList.get(i);
      if (reservation == reservationList.get(i)) {
        return reservationsExist;
      }
    }
    return false;
  }

  public List<LocalDate> dateRangeFromTo(LocalDate dateFrom, LocalDate dateTo) {
    List<LocalDate> listDate = new ArrayList<LocalDate>();
    LocalDate tmp = dateFrom;

    while (tmp.isBefore(dateTo) || tmp.equals(dateTo)) {
      listDate.add(tmp);
      tmp = tmp.plusDays(1);
    }
    return listDate;
  }
}
