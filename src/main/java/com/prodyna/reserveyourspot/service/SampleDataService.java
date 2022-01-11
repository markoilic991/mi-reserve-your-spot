package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.WorkStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class SampleDataService {

  private OfficeRoomService officeRoomService;

  private OfficeSpaceService officeSpaceService;

  private WorkStationService workStationService;

  @Autowired
  public SampleDataService(OfficeRoomService officeRoomService,
                           OfficeSpaceService officeSpaceService,
                           WorkStationService workStationService) {
    this.officeRoomService = officeRoomService;
    this.officeSpaceService = officeSpaceService;
    this.workStationService = workStationService;
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
    WorkStation workStationOne = new WorkStation();
    workStationOne.setCode("PD110011");
    workStationOne.setDescription("Windows Working Station");
    workStationOne.setOfficeRoom(officeRoomOne);
    WorkStation workStationTwo = new WorkStation();
    workStationTwo.setCode("PD220022");
    workStationTwo.setDescription("Linux Working Station");
    workStationTwo.setOfficeRoom(officeRoomOne);
    WorkStation workStationThree = new WorkStation();
    workStationThree.setCode("PD330033");
    workStationThree.setDescription("Windows Working Station");
    workStationThree.setOfficeRoom(officeRoomTwo);
    WorkStation workStationFour = new WorkStation();
    workStationFour.setCode("PD440044");
    workStationFour.setDescription("Linux Working Station");
    workStationFour.setOfficeRoom(officeRoomTwo);
    this.workStationService.save(workStationOne);
    this.workStationService.save(workStationTwo);
    this.workStationService.save(workStationThree);
    this.workStationService.save(workStationFour);

  }
}




