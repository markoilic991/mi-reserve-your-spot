package com.myproject.ReserveYourSpot.service;

import com.myproject.ReserveYourSpot.model.OfficeRoom;
import com.myproject.ReserveYourSpot.repository.OfficeRoomRepository;
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

    public OfficeRoom addRoom(OfficeRoom officeRoom){
        return officeRoomRepository.save(officeRoom);
    }

    public List<OfficeRoom> saveAll(List<OfficeRoom> rooms){
        return officeRoomRepository.saveAll(rooms);
    }

    public List<OfficeRoom> getAll(){
        return officeRoomRepository.findAll();
    }

    public OfficeRoom getById(int id){
        return officeRoomRepository.findById(id).orElse(null);
    }

    public String deleteById(int id){
        officeRoomRepository.deleteById(id);
        return "Room deleted!";
    }

    public OfficeRoom update(OfficeRoom officeRoom, int id){
        OfficeRoom oldRoom = officeRoomRepository.findById(id).orElse(null);
        oldRoom.setName(officeRoom.getName());
        oldRoom.setOrderNo(officeRoom.getOrderNo());
        return officeRoomRepository.save(oldRoom);
    }

}
