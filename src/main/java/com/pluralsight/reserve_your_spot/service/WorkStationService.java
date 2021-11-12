package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkStationService {

    private WorkStationRepository workStationRepository;

    @Autowired
    public WorkStationService(WorkStationRepository workStationRepository) {
        this.workStationRepository = workStationRepository;
    }

    //add one
    public WorkStation addOne(WorkStation workStation){
        return workStationRepository.save(workStation);
    }

    //add list of stations
    public List<WorkStation> saveAll(List<WorkStation> stations){
        return workStationRepository.saveAll(stations);
    }

    //get all
    public List<WorkStation> getAll(){
        return workStationRepository.findAll();
    }


    public WorkStation getById(int id){
        return workStationRepository.findById(id).orElse(null);
    }

    //delete
    public String deleteById(int id){
        workStationRepository.deleteById(id);
        return "Station deleted!";
    }

    //update
    public WorkStation update(WorkStation workStation, int id){
        WorkStation oldStation = workStationRepository.findById(id).orElse(null);
        oldStation.setUniqueCode(workStation.getUniqueCode());
        return workStationRepository.save(oldStation);
    }




}
