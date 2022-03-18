package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.UniqueValueException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ReservationService {

  private ReservationRepository reservationRepository;
  private WorkStationRepository workStationRepository;
  private UserRepository userRepository;
  private long numberBeforeDelete = 0;
  private long numberAfterDelete = 0;
  private long result = 0;

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

  public List<Reservation> findAllReservationsByUserIdAndDateRange(int userId, LocalDate dateFrom, LocalDate dateTo) {
    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalUser.isPresent()) {
      List<Reservation> reservations = reservationRepository.findReservationsByUserIdAndDateRange(userId, dateFrom, dateTo);
      return reservations;
    }
    throw new UniqueValueException("User with id " + userId + " does not exist in database!");
  }

  public Reservation save(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  public List<Reservation> saveReservations(int userId, int workStationId, LocalDate from, LocalDate to) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(workStationId);
    Optional<User> optionalUser = userRepository.findById(userId);

    if (checkIfReservationsExistInDateRange(workStationId, from, to)) {
      throw new UniqueValueException("Reservations by this date range already exist in database. Choose another data range!");
    }

    if (optionalWorkStation.isPresent() && optionalUser.isPresent()) {
      List<Reservation> reservations = dateRangeFromTo(from, to).stream()
              .map(date -> new Reservation(date, optionalUser.get(), optionalWorkStation.get()))
              .collect(Collectors.toList());
      return reservationRepository.saveAll(reservations);
    }
    throw new EntityNotFoundException("Some of input data do not exist in database, check again!");
  }

  public void deleteById(int id) {
    Optional<Reservation> optionalReservation = reservationRepository.findById(id);
    if (!optionalReservation.isPresent()) {
      throw new EntityNotFoundException("Reservation with id " + id + " does not exist in database!");
    }
    reservationRepository.deleteById(id);
    log.info("Reservation deleted!");
  }

  public void deleteAll(List<Integer> reservationsIds) {
    numberBeforeDelete = reservationRepository.count();
    reservationRepository.deleteAllReservations(reservationsIds);
    numberAfterDelete = reservationRepository.count();
    result = numberBeforeDelete - numberAfterDelete;
    log.info("Reservations deleted! Number of deleted reservations: " + result);
  }

  public void cancelReservation(int userId, int workStationId, LocalDate date) {
    Optional<Reservation> optionalReservation =
            Optional.ofNullable(reservationRepository.findByDateAndUserIdAndWorkStationId(date, userId, workStationId));
    if (!optionalReservation.isPresent()) {
      throw new EntityNotFoundException("Reservation has been already cancelled!");
    }
    reservationRepository.deleteByDateAndUserIdAndWorkStationId(date, userId, workStationId);
    log.info("Reservation cancelled successfully!");
  }

  //this method is used in SampleDataService class
  public boolean checkIfReservationExist(Reservation reservation) {
    LocalDate date = reservation.getDate();
    int workStationId = reservation.getWorkStation().getId();
    boolean reservationExist = false;

    Optional<Reservation> newReservation = Optional.ofNullable(reservationRepository.findByDateAndWorkStationId(date, workStationId));

    if (newReservation.isPresent()) {
      reservationExist = true;
    }
    return reservationExist;
  }

  public boolean checkIfReservationsExistInDateRange(int workStationId, LocalDate dateFrom, LocalDate dateTo) {
    List<Reservation> reservationList = reservationRepository.findReservationsByWorkStationIdAndDateRange(workStationId, dateFrom, dateTo);
    boolean reservationsExist = false;

    if (!reservationList.isEmpty()) {
      reservationsExist = true;
    }
    return reservationsExist;
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
