package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.ReservationAlreadyExistException;
import com.prodyna.reserveyourspot.exception.UserAlreadyReservedWorkStationException;
import com.prodyna.reserveyourspot.exception.WorkStationBusyException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.repository.ReservartionRepository;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    //checkIfReservationExistUsingStream(reservation);
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

  public Reservation findByDateAndWorkStationId(LocalDate date, int workStationId) {
    return reservartionRepository.findReservationByDateAndByWorkStationId(date, workStationId);
  }

  public Reservation findByDateAndUserId(LocalDate date, int userId) {
    return reservartionRepository.findReservationByDateAndByUserId(date, userId);
  }

  public Reservation findByDateAndUserIdAndWorkStationId(LocalDate date, int userId, int workStationId) {
    return reservartionRepository.findReservationByDateAndUserIdAndWorkStationId(date, userId, workStationId);
  }

  public void checkIfReservationExistUsingStream(Reservation reservation) throws ReservationAlreadyExistException {

    int userId = reservation.getUser().getId();
    String pattern = "yyyy-MM-dd";
    LocalDate date = reservation.getDate();
    String formattedDate = date.format(DateTimeFormatter.ofPattern(pattern));
    int workStationId = reservation.getWorkStation().getId();

    List<Reservation> reservations = reservartionRepository.findAll();

    reservations.stream()
            .filter(temp -> {
              if (temp.getDate().toString().equals(formattedDate) && temp.getUser().getId() == userId && temp.getWorkStation().getId() == workStationId) {

                throw new ReservationAlreadyExistException("Reservation already exist in database!");

              }
              if (temp.getDate().toString().equals(formattedDate) && temp.getUser().getId() != userId && temp.getWorkStation().getId() == workStationId) {

                throw new WorkStationBusyException("WorkStation busy! Choose another workStation for this date!");

              }

              if (temp.getDate().toString().equals(formattedDate) && temp.getUser().getId() == userId && temp.getWorkStation().getId() != workStationId) {

                throw new UserAlreadyReservedWorkStationException("User with id: " + userId + " has already reserved another workStation for this date! Only one workStation can be reserved by day!");
              }
              return true;
            })
            .forEach(System.out::println);

  }

  public boolean checkIfReservationExist(Reservation reservation) {

    int userId = reservation.getUser().getId();
    LocalDate date = reservation.getDate();
    int workStationId = reservation.getWorkStation().getId();
    boolean reservationExist = true;

    Optional<Reservation> newReservation1 = Optional.ofNullable(reservartionRepository.findReservationByDateAndByWorkStationId(date, workStationId));
    Optional<Reservation> newReservation2 = Optional.ofNullable(reservartionRepository.findReservationByDateAndByUserId(date, userId));

    if (newReservation1.isPresent()) {
      return reservationExist;
    } else if (newReservation2.isPresent()) {
      return reservationExist;
    }
    return false;
  }
}


