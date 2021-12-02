package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/officeSpaces")
public class GetWorkStationsByOfficeSpaceController {

  private OfficeSpaceService officeSpaceService;

  @Autowired
  public GetWorkStationsByOfficeSpaceController(OfficeSpaceService officeSpaceService) {
    this.officeSpaceService = officeSpaceService;
  }

  @GetMapping("/{id}")
  public Optional<OfficeSpace> getWorkStationsByOfficeSpace(@PathVariable("id") int id) {
    return officeSpaceService.findOfficeSpaceById(id);
  }

}

