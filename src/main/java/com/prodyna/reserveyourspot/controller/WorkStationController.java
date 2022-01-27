package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/work-stations")
public class WorkStationController {

  private WorkStationService workStationService;

  @Autowired
  public WorkStationController(WorkStationService workStationService) {
    this.workStationService = workStationService;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteById(@PathVariable int id) {
    workStationService.deleteById(id);
    return "WorkStation deleted successfully!";
  }

  @PutMapping("/{id}")
  public WorkStation update(@RequestBody WorkStation workStation, @PathVariable int id) {
    return workStationService.updateWorkStation(workStation, id);
  }
}
