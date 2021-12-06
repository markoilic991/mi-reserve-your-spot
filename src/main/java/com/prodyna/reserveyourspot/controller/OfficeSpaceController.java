package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/officeSpaces")
public class OfficeSpaceController {

  private OfficeSpaceService officeSpaceService;

  @Autowired
  public OfficeSpaceController(OfficeSpaceService officeSpaceService) {
    this.officeSpaceService = officeSpaceService;
  }

  @GetMapping("/")
  public List<OfficeSpace> findAll() {
    return officeSpaceService.findAll();
  }

  @PostMapping("/")
  public OfficeSpace save(OfficeSpace officeSpace) {
    return officeSpaceService.save(officeSpace);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    officeSpaceService.deleteById(id);
  }

}
