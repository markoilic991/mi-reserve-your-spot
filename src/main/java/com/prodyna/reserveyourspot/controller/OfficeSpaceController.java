package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
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
@RequestMapping("/api/office-spaces")
public class OfficeSpaceController {

  private OfficeSpaceService officeSpaceService;

  @Autowired
  public OfficeSpaceController(OfficeSpaceService officeSpaceService) {
    this.officeSpaceService = officeSpaceService;
  }

  @GetMapping
  public List<OfficeSpace> findAll() {
    List<OfficeSpace> officeSpaces = officeSpaceService.findAll();
    return officeSpaces;
  }

  @GetMapping("/{id}")
  public OfficeSpace findOfficeSpaceById(@PathVariable int id) {
    return officeSpaceService.findOfficeSpaceById(id);
  }

  @PostMapping
  public OfficeSpace save(OfficeSpace officeSpace) {
    return officeSpaceService.save(officeSpace);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    officeSpaceService.deleteById(id);
  }

  @PutMapping("/update/{id}")
  public OfficeSpace update(@RequestBody OfficeSpace officeSpace, @PathVariable int id){
    return officeSpaceService.updateOfficeSpace(officeSpace, id);
  }

}
