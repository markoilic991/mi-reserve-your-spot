package com.myproject.ReserveYourSpot.controller;

import com.myproject.ReserveYourSpot.model.WorkStation;
import com.myproject.ReserveYourSpot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
@RestController
@Validated
@RequestMapping("/stations")
public class WorkStationController {

    private WorkStationService workStationService;

    @Autowired
    // Advice: very good (autowiring with constructor), this can be covered with lombok annotations like @AllArgsConstructor
    public WorkStationController(WorkStationService workStationService) {
        this.workStationService = workStationService;
    }

    @PostMapping("/")
    // Comment: naming
    public WorkStation addOne(@Valid @RequestBody WorkStation workStation){
        return workStationService.addOne(workStation);
    }
    @PostMapping("/list")
    // Comment: naming
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
    // Comment: why separate id?
    public WorkStation update(@Valid @RequestBody WorkStation workStation,@PathVariable int id){
        return workStationService.update(workStation, id);
    }

}
