package com.myproject.ReserveYourSpot.service;

import com.myproject.ReserveYourSpot.model.WorkStation;
import com.myproject.ReserveYourSpot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStationService {

    private WorkStationRepository workStationRepository;

    @Autowired
    public WorkStationService(WorkStationRepository workStationRepository) {
        this.workStationRepository = workStationRepository;
    }

    public WorkStation addOne(WorkStation workStation){
        return workStationRepository.save(workStation);
    }

    public List<WorkStation> saveAll(List<WorkStation> stations){
        return workStationRepository.saveAll(stations);
    }

    public List<WorkStation> getAll(){
        return workStationRepository.findAll();
    }

    public WorkStation getById(int id){
        return workStationRepository.findById(id).orElse(null);
    }

    public String deleteById(int id){
        workStationRepository.deleteById(id);
        return "Station deleted!";
    }

    public WorkStation update(WorkStation workStation, int id){
        WorkStation oldStation = workStationRepository.findById(id).orElse(null);
        oldStation.setUniqueCode(workStation.getUniqueCode());
        return workStationRepository.save(oldStation);
    }




}
