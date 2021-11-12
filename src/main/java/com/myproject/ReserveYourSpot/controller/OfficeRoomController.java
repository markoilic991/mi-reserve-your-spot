package com.myproject.ReserveYourSpot.controller;

import com.myproject.ReserveYourSpot.model.OfficeRoom;
import com.myproject.ReserveYourSpot.service.OfficeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/")
    public OfficeRoom addOne(@Valid @RequestBody OfficeRoom officeRoom){
        return officeRoomService.addRoom(officeRoom);
    }

    @PostMapping("/list")
    public List<OfficeRoom> listOfRooms(@RequestBody List<OfficeRoom> rooms){
        return officeRoomService.saveAll(rooms);
    }

    @GetMapping("/")
    public List<OfficeRoom>getAll(){
        return officeRoomService.getAll();
    }


    @GetMapping("/{id}")
    public OfficeRoom getById(@PathVariable @Min(1) @Max(4) int id){
        return officeRoomService.getById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id){
        officeRoomService.deleteById(id);
    }


    @PutMapping("/{id}")
    public OfficeRoom update(@Valid @RequestBody OfficeRoom officeRoom, @PathVariable int id){
        return officeRoomService.update(officeRoom, id);
    }
}