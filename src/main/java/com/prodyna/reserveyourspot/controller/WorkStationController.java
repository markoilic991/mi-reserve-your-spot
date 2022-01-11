package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/work-stations")
public class WorkStationController {

  private WorkStationService workStationService;

  @Autowired
  public WorkStationController(WorkStationService workStationService) {
    this.workStationService = workStationService;
  }

  @GetMapping
  public List<WorkStation> findAll() {
    return workStationService.findAll();
  }

  @GetMapping("/{id}")
  public WorkStation findById(@PathVariable int id) {
    return workStationService.findById(id);
  }

  @PostMapping
  public WorkStation save(@RequestBody WorkStation workStation) {
    return workStationService.save(workStation);
  }

  @PostMapping("/list")
  public List<WorkStation> saveAll(@RequestBody List<WorkStation> stations) {
    return workStationService.saveAll(stations);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    workStationService.deleteById(id);
  }

  @PutMapping("/{id}")
  public WorkStation update(@RequestBody WorkStation workStation, @PathVariable int id) {
    return workStationService.updateWorkStation(workStation, id);
  }
}
