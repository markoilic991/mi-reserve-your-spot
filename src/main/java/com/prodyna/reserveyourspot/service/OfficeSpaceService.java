package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
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

  public OfficeSpace getOfficeByIdAndReservationDateRange(int id, LocalDate dateFrom, LocalDate dateTo) {
    Optional<OfficeSpace> optionalOfficeSpace = officeSpaceRepository.findById(id);
    if (optionalOfficeSpace.isPresent()) {
      return officeSpaceRepository.getOfficeByIdAndReservationDateRange(id, dateFrom, dateTo);
    }
    throw new EntityNotFoundException("OfficeSpace with id " + id + " does not exist!");
  }

  public OfficeSpace save(OfficeSpace officeSpace) {
    return officeSpaceRepository.save(officeSpace);
  }

  public List<OfficeSpace> saveAll(List<OfficeSpace> officeSpaces) {
    return officeSpaceRepository.saveAll(officeSpaces);
  }

  public String deleteById(int id) {
    officeSpaceRepository.deleteById((int) id);
    return "OfficeSpace deleted!";
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
}
