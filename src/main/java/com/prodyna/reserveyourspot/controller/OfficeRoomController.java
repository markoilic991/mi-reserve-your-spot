package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/office-rooms")
public class OfficeRoomController {

  private OfficeRoomService officeRoomService;

  @Autowired
  public OfficeRoomController(OfficeRoomService officeRoomService) {
    this.officeRoomService = officeRoomService;
  }

  @GetMapping
  public List<OfficeRoom> findAll() {
    return officeRoomService.findAll();
  }

  @GetMapping("/{id}")
  public OfficeRoom findById(@PathVariable int id) {
    return officeRoomService.findById(id);
  }

  @PostMapping
  public OfficeRoom save(@Valid @RequestBody OfficeRoom officeRoom) {
    return officeRoomService.save(officeRoom);
  }

  @PostMapping("/list")
  public List<OfficeRoom> saveAll(@RequestBody List<OfficeRoom> rooms) {
    return officeRoomService.saveAll(rooms);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    officeRoomService.deleteById(id);
  }

  @PutMapping("/update/{id}")
  public OfficeRoom update(@RequestBody OfficeRoom officeRoom, @PathVariable int id) {
    return officeRoomService.updateOfficeRoom(officeRoom, id);
  }
}
