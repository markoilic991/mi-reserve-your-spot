package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class OfficeRoomService {

  private OfficeRoomRepository officeRoomRepository;

  @Autowired
  public OfficeRoomService(OfficeRoomRepository officeRoomRepository) {
    this.officeRoomRepository = officeRoomRepository;
  }

  public OfficeRoom save(OfficeRoom officeRoom) {
    return officeRoomRepository.save(officeRoom);
  }

  public List<OfficeRoom> saveAll(List<OfficeRoom> rooms) {
    return officeRoomRepository.saveAll(rooms);
  }

  public List<OfficeRoom> findAll() {
    return officeRoomRepository.findAll();
  }

  public OfficeRoom findById(int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    return optionalOfficeRoom.get();
  }

  public String deleteById(int id) {
    officeRoomRepository.deleteById(id);
    return "Room deleted!";
  }

}
