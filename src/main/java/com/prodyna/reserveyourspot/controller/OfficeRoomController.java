package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/rooms")
public class OfficeRoomController {

  private OfficeRoomService officeRoomService;

  @Autowired
  public OfficeRoomController(OfficeRoomService officeRoomService) {
    this.officeRoomService = officeRoomService;
  }

  @PostMapping
  public OfficeRoom save(@Valid @RequestBody OfficeRoom officeRoom) {
    return officeRoomService.save(officeRoom);
  }

  @PostMapping("/list")
  public List<OfficeRoom> saveAll(@RequestBody List<OfficeRoom> rooms) {
    return officeRoomService.saveAll(rooms);
  }

  @GetMapping
  public List<OfficeRoom> findAll() {
    return officeRoomService.findAll();
  }

  @GetMapping("/{id}")
  public OfficeRoom findById(@PathVariable @Min(1) @Max(4) int id) {
    return officeRoomService.findById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    officeRoomService.deleteById(id);
  }

}
