package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.service.OfficeRoomService;
import com.prodyna.reserveyourspot.service.OfficeSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/office-spaces")
public class OfficeSpaceController {

  private OfficeSpaceService officeSpaceService;
  private OfficeRoomService officeRoomService;

  @Autowired
  public OfficeSpaceController(OfficeSpaceService officeSpaceService,
                               OfficeRoomService officeRoomService) {
    this.officeSpaceService = officeSpaceService;
    this.officeRoomService = officeRoomService;
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

  @GetMapping("/office-view")
  public OfficeSpace getOfficeByIdAndReservationDateRange(@RequestParam(value = "officeSpaceId") int id,
                                                          @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                          @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
    return officeSpaceService.getOfficeSpaceByIdAndReservationDateRange(id, dateFrom, dateTo);
  }

  @GetMapping("/{officeSpaceId}/office-rooms")
  public List<OfficeRoom> findByOfficeSpaceId(@PathVariable("officeSpaceId") int id) {
    return officeRoomService.findByOfficeSpaceId(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OfficeSpace saveOfficeSpace(@Valid @RequestBody OfficeSpace officeSpace) {
    return officeSpaceService.save(officeSpace);
  }

  @PostMapping("/{officeSpaceId}/office-rooms")
  @ResponseStatus(HttpStatus.CREATED)
  public OfficeRoom saveOfficeRoom(@PathVariable("officeSpaceId") int officeSpaceId, @RequestBody OfficeRoom officeRoom) {
    return officeRoomService.saveOfficeRoom(officeSpaceId, officeRoom);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteById(@PathVariable int id) {
    officeSpaceService.deleteById(id);
    return "OfficeSpace deleted successfully!";
  }

  @PutMapping("/{id}")
  public OfficeSpace update(@RequestBody OfficeSpace officeSpace, @PathVariable int id) {
    return officeSpaceService.updateOfficeSpace(officeSpace, id);
  }
}
