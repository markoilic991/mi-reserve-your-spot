package com.pluralsight.reserve_your_spot.controller;

import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stations")
public class WorkStationController {

    private WorkStationService workStationService;

    @Autowired
    public WorkStationController(WorkStationService workStationService) {
        this.workStationService = workStationService;
    }

    @PostMapping("/")
    public WorkStation addOne(@Valid @RequestBody WorkStation workStation){
        return workStationService.addOne(workStation);
    }
    @PostMapping("/list")
    public List<WorkStation> listOfRooms(@RequestBody List<WorkStation> stations){
        return workStationService.saveAll(stations);
    }

    @GetMapping("/")
    public List<WorkStation>getAll(){
        return workStationService.getAll();
    }

    @GetMapping("/{id}")
    public  WorkStation getById(@PathVariable int id){
        return workStationService.getById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        workStationService.deleteById(id);
    }


    @PutMapping("/{id}")
    public WorkStation update(@Valid @RequestBody WorkStation workStation,@PathVariable int id){
        return workStationService.update(workStation, id);
    }
}
