package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.UniqueValueException;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.repository.OfficeRoomRepository;
import com.prodyna.reserveyourspot.repository.OfficeSpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

  public OfficeRoom saveOfficeRoom(int officeSpaceId, OfficeRoom officeRoom) {
    Optional<OfficeSpace> officeSpace = officeSpaceRepository.findById(officeSpaceId);
    if (!officeSpace.isPresent()) {
      throw new EntityNotFoundException("OfficeSpace with id " + officeSpaceId + " does not exist! OfficeRoom can not be saved!");
    } else if (checkIfOfficeRoomExist(officeRoom)) {
      throw new UniqueValueException("OfficeRoom has unique code! Try another one!");
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
    throw new EntityNotFoundException("OfficeRoom with id " + id + " does not exist!");
  }

  public List<OfficeRoom> findByOfficeSpaceId(int id) {
    Optional<OfficeSpace> officeSpace = officeSpaceRepository.findById(id);
    if (!officeSpace.isPresent()) {
      throw new EntityNotFoundException("OfficeSpace with id " + id + " does not exist!");
    }
    return officeRoomRepository.findByOfficeSpaceId(id);
  }

  public void deleteById(int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (!optionalOfficeRoom.isPresent()) {
      throw new EntityNotFoundException("OfficeRoom with id " + id + " does not exist in database!");
    }
    officeRoomRepository.deleteById(id);
    log.info("OfficeRoom deleted successfully!");
  }

  public OfficeRoom updateOfficeRoom(OfficeRoom officeRoom, int id) {
    Optional<OfficeRoom> optionalOfficeRoom = officeRoomRepository.findById(id);
    if (!optionalOfficeRoom.isPresent()) {
      throw new EntityNotFoundException("OfficeRoom with id " + id + "does not exist in database!");
    }
    OfficeRoom officeRoomUpdated = optionalOfficeRoom.get();
    officeRoomUpdated.setName(officeRoom.getName());
    officeRoomUpdated.setCode(officeRoom.getCode());
    return officeRoomRepository.save(officeRoomUpdated);
  }

  public boolean checkIfOfficeRoomExist(OfficeRoom officeRoom) {
    int code = officeRoom.getCode();
    boolean officeRoomExist = false;
    Optional<OfficeRoom> optionalOfficeRoom = Optional.ofNullable(officeRoomRepository.findByCode(code));

    if (optionalOfficeRoom.isPresent()) {
      officeRoomExist = true;
    }
    return officeRoomExist;
  }
}
