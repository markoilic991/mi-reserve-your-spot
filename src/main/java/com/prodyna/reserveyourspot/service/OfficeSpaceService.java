package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.UniqueValueException;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
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

@Service
@Slf4j
@Transactional
public class OfficeSpaceService {

  private OfficeSpaceRepository officeSpaceRepository;
  private WorkStationRepository workStationRepository;
  private ReservationRepository reservationRepository;

  @Autowired
  public OfficeSpaceService(OfficeSpaceRepository officeSpaceRepository, WorkStationRepository workStationRepository, ReservationRepository reservationRepository) {
    this.officeSpaceRepository = officeSpaceRepository;
    this.workStationRepository = workStationRepository;
    this.reservationRepository = reservationRepository;
  }

  public List<OfficeSpace> findAll() {
    List<OfficeSpace> officeSpaces = officeSpaceRepository.findAll();
    return officeSpaces;
  }

  public OfficeSpace findOfficeSpaceById(int id) {
    Optional<OfficeSpace> optionalOfficeSpace = officeSpaceRepository.findById(id);
    if (optionalOfficeSpace.isPresent()) {
      return optionalOfficeSpace.get();
    }
    throw new EntityNotFoundException("OfficeSpace with id " + id + " does not exist!");
  }

  public OfficeSpace getOfficeSpaceByIdAndReservationDateRange(int id, LocalDate dateFrom, LocalDate dateTo) {
    List<WorkStation> workStationList = workStationRepository.findAll();
    List<Reservation> reservationList = reservationRepository.findAllReservationByDateRange(dateFrom, dateTo);
    List<LocalDate> dateList = dateRangeFromTo(dateFrom, dateTo);

    for (Reservation reservation : reservationList) {
      for (WorkStation workStation : workStationList) {
        for (LocalDate date : dateList) {
          if ((reservation.getWorkStation().getId() == workStation.getId()) && (date.isEqual(reservation.getDate()))) {
            workStation.setHasReservation(true);
          } else {
            workStation.isHasReservation();
          }
        }
      }
    }
    Optional<OfficeSpace> optionalOfficeSpace = officeSpaceRepository.findById(id);
    if (optionalOfficeSpace.isPresent()) {
      return officeSpaceRepository.getOfficeSpaceByIdAndReservationDateRange(id, dateFrom, dateTo);
    }
    throw new EntityNotFoundException("OfficeSpace with id " + id + " does not exist!");
  }

  public OfficeSpace save(OfficeSpace officeSpace) {
    if (checkIfOfficeSpaceExist(officeSpace)) {
      throw new UniqueValueException("Office space with name xyz already exist, try another one...");
    }
    return officeSpaceRepository.save(officeSpace);
  }

  public void deleteById(int id) {
    Optional<OfficeSpace> optionalOfficeSpace = officeSpaceRepository.findById(id);
    if (!optionalOfficeSpace.isPresent()) {
      throw new EntityNotFoundException("OfficeSpace with id " + id + " does not exist in database!");
    }
    officeSpaceRepository.deleteById((int) id);
    log.info("OfficeSpace deleted!");
  }

  public OfficeSpace updateOfficeSpace(OfficeSpace officeSpace, int id) {
    Optional<OfficeSpace> optionalOfficeSpace = officeSpaceRepository.findById(id);
    if (!optionalOfficeSpace.isPresent()) {
      throw new EntityNotFoundException("OfficeSpace does not exist in database!");
    }
    optionalOfficeSpace.get().setName(officeSpace.getName());
    optionalOfficeSpace.get().setDescription(officeSpace.getDescription());
    return officeSpaceRepository.save(optionalOfficeSpace.get());
  }

  public boolean checkIfOfficeSpaceExist(OfficeSpace officeSpace) {
    String name = officeSpace.getName();
    boolean officeSpaceExist = false;
    Optional<OfficeSpace> optionalOfficeSpace = Optional.ofNullable(officeSpaceRepository.findByName(name));

    if (optionalOfficeSpace.isPresent()) {
      officeSpaceExist = true;
    }
    return officeSpaceExist;
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
