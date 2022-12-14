package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
import com.prodyna.reserveyourspot.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/office-rooms")
public class OfficeRoomController {

  private OfficeRoomService officeRoomService;
  private WorkStationService workStationService;

  @Autowired
  public OfficeRoomController(OfficeRoomService officeRoomService,
                              WorkStationService workStationService) {
    this.officeRoomService = officeRoomService;
    this.workStationService = workStationService;
  }

  @GetMapping("/{id}")
  public OfficeRoom findById(@PathVariable int id) {
    return officeRoomService.findById(id);
  }

  @GetMapping("/{officeRoomId}/work-stations")
  public List<WorkStation> findByOfficeRoomId(@PathVariable("officeRoomId") int id) {
    return workStationService.findByOfficeRoomId(id);
  }

  @PostMapping("/{officeRoomId}/work-stations")
  @ResponseStatus(HttpStatus.CREATED)
  public WorkStation saveWorkStation(@PathVariable("officeRoomId") int officeRoomId, @RequestBody WorkStation workStation) {
    return workStationService.saveWorkStation(workStation, officeRoomId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteById(@PathVariable int id) {
    officeRoomService.deleteById(id);
    return "OfficeRoom deleted successfully!";
  }

  @PutMapping("/{id}")
  public OfficeRoom update(@RequestBody OfficeRoom officeRoom, @PathVariable int id) {
    return officeRoomService.updateOfficeRoom(officeRoom, id);
  }
}
