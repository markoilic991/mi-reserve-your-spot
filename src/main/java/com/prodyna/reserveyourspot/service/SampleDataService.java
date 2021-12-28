package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class SampleDataService {

  private OfficeRoomService officeRoomService;

  private OfficeSpaceService officeSpaceService;

  @Autowired
  public SampleDataService(OfficeRoomService officeRoomService, OfficeSpaceService officeSpaceService) {
    this.officeRoomService = officeRoomService;
    this.officeSpaceService = officeSpaceService;
  }

  @PostConstruct
  public void createOfficeSpace() {
    OfficeSpace officeSpace = new OfficeSpace();
    officeSpace.setName("PRODYNA DOO");
    officeSpace.setDescription("PRODYNA Business garden 14th floor");
    this.officeSpaceService.save(officeSpace);
    OfficeRoom officeRoomOne = new OfficeRoom();
    officeRoomOne.setName("JAVA");
    officeRoomOne.setCode(1);
    officeRoomOne.setOfficeSpace(officeSpace);
    OfficeRoom officeRoomTwo = new OfficeRoom();
    officeRoomTwo.setName("QA");
    officeRoomTwo.setCode(2);
    officeRoomTwo.setOfficeSpace(officeSpace);
    this.officeRoomService.save(officeRoomOne);
    this.officeRoomService.save(officeRoomTwo);
  }

}




