package com.pluralsight.reserve_your_spot.controller;

import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class WorkStationController {

    private WorkStationService workStationService;

    @Autowired
    public WorkStationController(WorkStationService workStationService) {
        this.workStationService = workStationService;
    }
    // add one
    @PostMapping("/")
    public WorkStation addOne(@RequestBody WorkStation workStation){
        return workStationService.addOne(workStation);
    }

    //get all
    @GetMapping("/")
    public List<WorkStation>getAll(){
        return workStationService.getAll();
    }

    @GetMapping("/{id}")
    public  WorkStation getById(@PathVariable int id){
        return workStationService.getById(id);
    }

    //delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        workStationService.deleteById(id);
    }

    //update
    @PutMapping("update")
    public WorkStation update(@RequestBody WorkStation workStation){
        return workStationService.update(workStation);
    }
}
