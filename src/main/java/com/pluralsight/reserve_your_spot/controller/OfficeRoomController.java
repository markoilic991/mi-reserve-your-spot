package com.pluralsight.reserve_your_spot.controller;

import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.service.OfficeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
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

    //add
    @PostMapping("/")
    public OfficeRoom addOne(@Valid @RequestBody OfficeRoom officeRoom){
        return officeRoomService.addRoom(officeRoom);
    }

    //get all
    @GetMapping("/")
    public List<OfficeRoom>getAll(){
        return officeRoomService.getAll();
    }

    //get by Id
    @GetMapping("/{id}")
    public OfficeRoom getById(@PathVariable @Min(1) @Max(4) int id){
        return officeRoomService.getById(id);
    }


    //delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        officeRoomService.deleteById(id);
    }

    //update
    @PutMapping("/update")
    public OfficeRoom update(@Valid @RequestBody OfficeRoom officeRoom){
        return officeRoomService.update(officeRoom);
    }
}
