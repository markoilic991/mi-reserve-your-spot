package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Slf4j
@Component
public class SampleDataService {

  private OfficeRoomService officeRoomService;

  private OfficeSpaceService officeSpaceService;

  private WorkStationService workStationService;

  private UserService userService;

  private ReservationService reservationService;

  @Autowired
  public SampleDataService(OfficeRoomService officeRoomService,
                           OfficeSpaceService officeSpaceService,
                           WorkStationService workStationService,
                           UserService userService,
                           ReservationService reservationService) {
    this.officeRoomService = officeRoomService;
    this.officeSpaceService = officeSpaceService;
    this.workStationService = workStationService;
    this.userService = userService;
    this.reservationService = reservationService;
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
    User userMarko = new User();
    userMarko.setName("Marko Ilic");
    userMarko.setEmail("marko.ilic@prodyna.com");
    User userMiroslav = new User();
    userMiroslav.setName("Miroslav Perovic");
    userMiroslav.setEmail("miroslav.perovic@prodyna.com");
    this.userService.save(userMarko);
    this.userService.save(userMiroslav);
    Reservation reservationOne = new Reservation();
    String dateOne = "2021-12-30";
    LocalDate parseDateOne = LocalDate.parse(dateOne);
    reservationOne.setDate(parseDateOne);
    reservationOne.setUser(userMarko);
    reservationOne.setWorkStation(workStationThree);
    Reservation reservationTwo = new Reservation();
    String dateTwo = "2022-01-28";
    LocalDate parseDateTwo = LocalDate.parse(dateTwo);
    reservationTwo.setDate(parseDateTwo);
    reservationTwo.setUser(userMiroslav);
    reservationTwo.setWorkStation(workStationTwo);
    this.reservationService.save(reservationOne);
    this.reservationService.save(reservationTwo);
  }
}




