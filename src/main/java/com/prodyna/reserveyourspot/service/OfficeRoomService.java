package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.OfficeRoomNotFoundException;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OfficeRoomService {

  private OfficeRoomRepository officeRoomRepository;
  private OfficeSpaceRepository officeSpaceRepository;

  @Autowired
  public OfficeRoomService(OfficeRoomRepository officeRoomRepository,
                           OfficeSpaceRepository officeSpaceRepository) {
    this.officeRoomRepository = officeRoomRepository;
    this.officeSpaceRepository = officeSpaceRepository;
  }

  public OfficeRoom save(OfficeRoom officeRoom) {
    return officeRoomRepository.save(officeRoom);
  }

  public List<OfficeRoom> saveAll(List<OfficeRoom> rooms) {
    return officeRoomRepository.saveAll(rooms);
  }

  public OfficeRoom saveOfficeRoom(int officeSpaceId, OfficeRoom officeRoom) {
    Optional<OfficeSpace> officeSpace = officeSpaceRepository.findById(officeSpaceId);
    if (!officeSpace.isPresent()) {
      throw new EntityNotFoundException("OfficeSpace with id " + officeSpaceId + " does not exist! OfficeRoom can not be saved!");
    }
    officeRoom.setOfficeSpace(officeSpace.get());
    return officeRoomRepository.save(officeRoom);
  }

  public List<OfficeRoom> findAll() {
    return officeRoomRepository.findAll();
  }

  public OfficeRoom findById(int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (optionalOfficeRoom.isPresent()) {
      return optionalOfficeRoom.get();
    }
    throw new OfficeRoomNotFoundException("OfficeRoom with id: " + id + " does not exist!");
  }

  public List<OfficeRoom> findByOfficeSpaceId(int id) {
    return officeRoomRepository.findByOfficeSpaceId(id);
  }

  public String deleteById(int id) {
    officeRoomRepository.deleteById(id);
    return "Room deleted!";
  }

  public OfficeRoom updateOfficeRoom(OfficeRoom officeRoom, int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (!optionalOfficeRoom.isPresent()) {
      throw new EntityNotFoundException("OfficeRoom does not exist in database!");
    }
    OfficeRoom officeRoomUpdated = optionalOfficeRoom.get();
    officeRoomUpdated.setName(officeRoom.getName());
    officeRoomUpdated.setCode(officeRoom.getCode());
    return officeRoomRepository.save(officeRoomUpdated);
  }
}
