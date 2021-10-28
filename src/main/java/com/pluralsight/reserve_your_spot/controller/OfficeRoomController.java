package com.pluralsight.reserve_your_spot.controller;

import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.service.OfficeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class OfficeRoomController {

    private OfficeRoomService officeRoomService;

    @Autowired
    public OfficeRoomController(OfficeRoomService officeRoomService) {
        this.officeRoomService = officeRoomService;
    }

    //add
    @PostMapping("/")
    public OfficeRoom addOne(@RequestBody OfficeRoom officeRoom){
        return officeRoomService.addRoom(officeRoom);
    }

    //get all
    @GetMapping("/")
    public List<OfficeRoom>getAll(){
        return officeRoomService.getAll();
    }

    //get by Id
    @GetMapping("/{id}")
    public OfficeRoom getById(@PathVariable int id){
        return officeRoomService.getById(id);
    }

    //delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        officeRoomService.deleteById(id);
    }

    //update
    @PutMapping("/update")
    public OfficeRoom update(@RequestBody OfficeRoom officeRoom){
        return officeRoomService.update(officeRoom);
    }
}
