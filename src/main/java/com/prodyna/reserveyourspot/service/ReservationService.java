package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.ReservationAlreadyExistException;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.ReservationRepository;
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

@Slf4j
@Service
@Transactional
public class ReservationService {

  private ReservationRepository reservationRepository;
  private WorkStationRepository workStationRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository,
                            WorkStationRepository workStationRepository) {
    this.reservationRepository = reservationRepository;
    this.workStationRepository = workStationRepository;
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

  public Reservation saveReservation(int workStationId, Reservation reservation) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(workStationId);
    if (!optionalWorkStation.isPresent()) {
      throw new EntityNotFoundException("WorkStation with id " + workStationId + " does not exist! Reservation can not be saved!");
    }
    WorkStation workStationToSave = optionalWorkStation.get();
    reservation.setWorkStation(workStationToSave);
    if (checkIfReservationExist(reservation)) {
      throw new ReservationAlreadyExistException("Reservation already exists in database!!!");
    } else {
      return reservationRepository.save(reservation);
    }
  }

  public List<Reservation> saveReservations(int workStationId, LocalDate from, LocalDate to) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(workStationId);
    if (optionalWorkStation.isPresent()) {

      List<Reservation> reservations = dateRangeFromTo(from, to).stream()
              .map(date -> new Reservation(date, optionalWorkStation.get()))
              .collect(Collectors.toList());

      return reservationRepository.saveAll(reservations);
    }
    throw new EntityNotFoundException("Some of input data do not exist in database, check again!");
  }

  public String deleteById(int id) {
    reservationRepository.deleteById((int) id);
    return "Reservations deleted!";
  }

  public void cancelReservation(int workStationId, LocalDate date) {
    reservationRepository.deleteByDateAndWorkStationId(date, workStationId);
    log.info("Reservation cancelled!!!");
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
    int workStationId = reservation.getWorkStation().getId();
    boolean reservationExist = true;

    Optional<Reservation> newReservation1 = Optional.ofNullable(reservationRepository.findByDateAndWorkStationId(date, workStationId));

    if (newReservation1.isPresent()) {
      return reservationExist;
    } else {
      return false;
    }
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
