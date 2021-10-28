package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.repository.OfficeRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeRoomService {

    private OfficeRoomRepository officeRoomRepository;

    @Autowired
    public OfficeRoomService(OfficeRoomRepository officeRoomRepository) {
        this.officeRoomRepository = officeRoomRepository;
    }

    //add one
    public OfficeRoom addRoom(OfficeRoom officeRoom){
        return officeRoomRepository.save(officeRoom);
    }

    //list all
    public List<OfficeRoom> getAll(){
        return officeRoomRepository.findAll();
    }


    public OfficeRoom getById(int id){
        return officeRoomRepository.findById(id).orElse(null);
    }

    //delete
    public String deleteById(int id){
        officeRoomRepository.deleteById(id);
        return "Room deleted!";
    }

    //update
    public OfficeRoom update(OfficeRoom officeRoom){
        OfficeRoom oldRoom = officeRoomRepository.getById(officeRoom.getId());
        oldRoom.setName(officeRoom.getName());
        oldRoom.setOrderNo(officeRoom.getOrderNo());
        return officeRoomRepository.save(oldRoom);
    }
}
