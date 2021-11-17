package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/workStations")
public class WorkStationController {

    private WorkStationService workStationService;

    @Autowired
    public WorkStationController(WorkStationService workStationService) {
        this.workStationService = workStationService;
    }

    @PostMapping("/")
    public WorkStation addOne(@Valid @RequestBody WorkStation workStation) {
        return workStationService.save(workStation);
    }

    @PostMapping("/list")
    public List<WorkStation> listOfRooms(@RequestBody List<WorkStation> stations) {
        return workStationService.saveAll(stations);
    }

    @GetMapping("/")
    public List<WorkStation> findAll() {
        return workStationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<WorkStation> findById(@PathVariable int id) {
        return workStationService.findById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        workStationService.deleteById(id);
    }

}
