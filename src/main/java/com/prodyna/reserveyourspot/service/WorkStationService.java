package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.UniqueValueException;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class WorkStationService {

  private WorkStationRepository workStationRepository;
  private OfficeRoomRepository officeRoomRepository;

  @Autowired
  public WorkStationService(WorkStationRepository workStationRepository,
                            OfficeRoomRepository officeRoomRepository) {
    this.workStationRepository = workStationRepository;
    this.officeRoomRepository = officeRoomRepository;
  }

  public List<WorkStation> findAll() {
    return workStationRepository.findAll();
  }

  public WorkStation findById(int id) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(id);
    if (optionalWorkStation.isPresent()) {
      return optionalWorkStation.get();
    }
    throw new EntityNotFoundException("WorkStation with id " + id + " does not exist!");
  }

  public WorkStation findByCode(String code){
    Optional<WorkStation> optionalWorkStation = Optional.ofNullable(workStationRepository.findByCode(code));
    if (optionalWorkStation.isPresent()){
      return optionalWorkStation.get();
    }
    throw new EntityNotFoundException("WorkStation with code " + code + " does not exist!");
  }

  public List<WorkStation> findByOfficeRoomId(int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (!optionalOfficeRoom.isPresent()) {
      throw new EntityNotFoundException("OfficeRoom with id: " + id + " does not exist!");
    }
    return workStationRepository.findByOfficeRoomId(id);
  }

  public WorkStation save(WorkStation workStation) {
    return workStationRepository.save(workStation);
  }

  public WorkStation saveWorkStation(WorkStation workStation, int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (!optionalOfficeRoom.isPresent()) {
      throw new EntityNotFoundException("OfficeRoom with id " + id + " does not exist! WorkStation can not be saved!");
    } else if (checkIfWorkStationExist(workStation)) {
      throw new UniqueValueException("WorkStation has unique code! Try another one!");
    }
    workStation.setOfficeRoom(optionalOfficeRoom.get());
    return workStationRepository.save(workStation);
  }

  public void deleteById(int id) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(id);
    if (!optionalWorkStation.isPresent()) {
      throw new EntityNotFoundException("WorkStation with id " + id + " does not exist in database!");
    }
    workStationRepository.deleteById(id);
    log.info("WorkStation deleted successfully!");
  }

  public WorkStation updateWorkStation(WorkStation workStation, int id) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(id);
    if (!optionalWorkStation.isPresent()) {
      throw new EntityNotFoundException("WorkStation with id " + id + " does not exist in database!");
    }
    WorkStation workStationUpdated = optionalWorkStation.get();
    workStationUpdated.setDescription(workStation.getDescription());
    workStationUpdated.setCode(workStation.getCode());
    return workStationRepository.save(workStationUpdated);
  }

  public boolean checkIfWorkStationExist(WorkStation workStation) {
    String code = workStation.getCode();
    boolean workStationExist = false;
    Optional<WorkStation> optionalWorkStation = Optional.ofNullable(workStationRepository.findByCode(code));

    if (optionalWorkStation.isPresent()) {
      workStationExist = true;
    }
    return workStationExist;
  }
}
