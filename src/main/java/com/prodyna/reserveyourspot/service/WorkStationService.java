package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
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

  public WorkStation save(WorkStation workStation) {
    return workStationRepository.save(workStation);
  }

  public List<WorkStation> saveAll(List<WorkStation> stations) {
    return workStationRepository.saveAll(stations);
  }

  public List<WorkStation> findAll() {
    return workStationRepository.findAll();
  }

  public WorkStation findById(int id) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(id);
    if (optionalWorkStation.isPresent()) {
      return optionalWorkStation.get();
    }
    throw new EntityNotFoundException("WorkStation with id: " + id + " does not exist!");
  }

  public List<WorkStation> findByOfficeRoomId(int id) {
    return workStationRepository.findByOfficeRoomId(id);
  }

  public String deleteById(int id) {
    workStationRepository.deleteById(id);
    return "Station deleted!";
  }

  public WorkStation updateWorkStation(WorkStation workStation, int id) {
    Optional<WorkStation> optionalWorkStation = workStationRepository.findById(id);
    if (!optionalWorkStation.isPresent()) {
      throw new EntityNotFoundException("Working Station does not exist in database!");
    }
    optionalWorkStation.get().setDescription(workStation.getDescription());
    optionalWorkStation.get().setCode(workStation.getCode());
    return workStationRepository.save(optionalWorkStation.get());
  }

  public WorkStation saveWorkStation(WorkStation workStation, int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (!optionalOfficeRoom.isPresent()) {
      throw new EntityNotFoundException("OfficeRoom with id " + id + " does not exist! WorkStation can not be saved!");
    }
    workStation.setOfficeRoom(optionalOfficeRoom.get());
    return workStationRepository.save(workStation);
  }
}
