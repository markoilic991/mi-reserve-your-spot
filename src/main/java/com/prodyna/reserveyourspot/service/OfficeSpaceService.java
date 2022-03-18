package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.UniqueValueException;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class OfficeSpaceService {

  private OfficeSpaceRepository officeSpaceRepository;

  @Autowired
  public OfficeSpaceService(OfficeSpaceRepository officeSpaceRepository) {
    this.officeSpaceRepository = officeSpaceRepository;
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
}
