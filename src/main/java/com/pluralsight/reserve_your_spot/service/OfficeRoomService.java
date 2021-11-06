package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.exception.NameNotValidException;
import com.pluralsight.reserve_your_spot.exception.OfficeNotFoundException;
import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.OfficeRoomRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        return officeRoomRepository.findById(id).orElseThrow(()-> new OfficeNotFoundException("OfficeRoom do not exist with id: " + id));
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
