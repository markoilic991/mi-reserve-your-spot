package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.ListOfReservationsAlreadyExistException;
import com.prodyna.reserveyourspot.exception.ReservationAlreadyExistException;
import com.prodyna.reserveyourspot.exception.ReservationNotFoundException;
import com.prodyna.reserveyourspot.exception.WorkStationBusyException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
public class ReservationService {

  private ReservationRepository reservationRepository;
  private UserRepository userRepository;
  private WorkStationRepository workStationRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository,
                            UserRepository userRepository,
                            WorkStationRepository workStationRepository) {
    this.reservationRepository = reservationRepository;
    this.userRepository = userRepository;
    this.workStationRepository = workStationRepository;
  }

  public Reservation save(Reservation reservation) throws WorkStationBusyException {
    if (checkIfReservationExist(reservation)) {
      throw new ReservationAlreadyExistException("Reservation already exists in database!!!");
    } else {
      return reservationRepository.save(reservation);
    }
  }

  public List<Reservation> saveAll(List<Reservation> revs) {
    if (checkIfReservationsExist(revs)) {
      throw new ListOfReservationsAlreadyExistException("Reservations already exist in database!!!");
    } else {
      return reservationRepository.saveAll(revs);
    }
  }

  public List<Reservation> findAll() {
    List<Reservation> reservations = reservationRepository.findAll();
    return reservations;
  }

  public Reservation findById(int id) {
    Optional<Reservation> optionalReservation = reservationRepository.findById(id);
    if (optionalReservation.isPresent()) {
      return optionalReservation.get();
    }
      throw new ReservationNotFoundException("Reservation with id: " + " does not exist!");

  }

  public String deleteById(int id) {
    reservationRepository.deleteById((int) id);
    return "Reservations deleted!";
  }

  public List<Reservation> findAllReservationsByDateRange(LocalDate dateFrom, LocalDate dateTo) {
    return reservationRepository.findAllReservationByDateRange(dateFrom, dateTo);
  }

  public Reservation findByDateAndWorkStationId(LocalDate date, int workStationId) {
    return reservationRepository.findByDateAndWorkStationId(date, workStationId);
  }

  public Reservation findByDateAndUserId(LocalDate date, int userId) {
    return reservationRepository.findByDateAndUserId(date, userId);
  }

  public boolean checkIfReservationExist(Reservation reservation) {

    int userId = reservation.getUser().getId();
    LocalDate date = reservation.getDate();
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

  public boolean checkIfReservationsExist(List<Reservation> reservations) {

    boolean reservationsExist = true;
    Optional<Reservation> reservation = null;

    for (int i = 0; i < reservations.size(); i++) {
      reservation = Optional.ofNullable(reservations.get(i));
    }

    reservation = Optional.ofNullable(reservationRepository.findByDateAndWorkStationId(reservation.get().getDate(), reservation.get().getWorkStation().getId()));

    if (reservation.isPresent()) {
      return reservationsExist;
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

  //method to write data into database by date range
  public List<Reservation> saveReservations(int userId, int workStationId, LocalDate from, LocalDate to) {

    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(workStationId);
    if (optionalUser.isPresent() && optionalWorkStation.isPresent()) {

      List<Reservation> reservations = dateRangeFromTo(from, to).stream()
              .map(date -> new Reservation(date, optionalUser.get(), optionalWorkStation.get()))
              .collect(Collectors.toList());

      return reservationRepository.saveAll(reservations);
    }
    throw new EntityNotFoundException("Some of input data do not exist in database, check again!");
  }

  //method to cancel/delete reservation for particular date
  public void cancelReservation(int userId, int workStationId, LocalDate date) {

    Reservation reservation = reservationRepository.findByUserIdAndWorkStationIdAndDate(date, workStationId, userId);
    reservationRepository.delete(reservation);

    log.info("Reservation cancelled!!!");

  }

}




