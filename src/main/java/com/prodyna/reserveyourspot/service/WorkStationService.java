package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.WorkStationNotFoundException;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class WorkStationService {

  private WorkStationRepository workStationRepository;

  @Autowired
  public WorkStationService(WorkStationRepository workStationRepository) {
    this.workStationRepository = workStationRepository;
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
    } else {
      throw new WorkStationNotFoundException("WorkStation with id: " + " does not exist!");
    }
  }

  public String deleteById(int id) {
    workStationRepository.deleteById(id);
    return "Station deleted!";
  }

}
