package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.Reservation;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.repository.WorkStationRepository;
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

  private UserRepository userRepository;

  private WorkStationRepository workStationRepository;

  @Autowired
  public SampleDataService(OfficeRoomService officeRoomService,
                           OfficeSpaceService officeSpaceService,
                           WorkStationService workStationService,
                           UserService userService,
                           ReservationService reservationService,
                           UserRepository userRepository,
                           WorkStationRepository workStationRepository) {
    this.officeRoomService = officeRoomService;
    this.officeSpaceService = officeSpaceService;
    this.workStationService = workStationService;
    this.userService = userService;
    this.reservationService = reservationService;
    this.userRepository = userRepository;
    this.workStationRepository = workStationRepository;
  }

  @PostConstruct
  public void createOfficeSpace() {
    OfficeSpace officeSpace = new OfficeSpace();
    officeSpace.setName("PRODYNA DOO");
    officeSpace.setDescription("PRODYNA Business garden 14th floor");

    if (!officeSpaceService.checkIfOfficeSpaceExist(officeSpace)) {
      this.officeSpaceService.save(officeSpace);
    }

    OfficeRoom officeRoomJava = new OfficeRoom();
    officeRoomJava.setName("JAVA");
    officeRoomJava.setCode(1);
    officeRoomJava.setOfficeSpace(officeSpace);

    if (!officeRoomService.checkIfOfficeRoomExist(officeRoomJava)) {
      this.officeRoomService.save(officeRoomJava);
    }

    OfficeRoom officeRoomQa = new OfficeRoom();
    officeRoomQa.setName("QA");
    officeRoomQa.setCode(2);
    officeRoomQa.setOfficeSpace(officeSpace);

    if (!officeRoomService.checkIfOfficeRoomExist(officeRoomQa)) {
      this.officeRoomService.save(officeRoomQa);
    }

    WorkStation workStationOne = new WorkStation();
    workStationOne.setCode("PD110011");
    workStationOne.setDescription("Windows Working Station");
    workStationOne.setOfficeRoom(officeRoomJava);

    if (!workStationService.checkIfWorkStationExist(workStationOne)) {
      this.workStationService.save(workStationOne);
    }

    WorkStation workStationTwo = new WorkStation();
    workStationTwo.setCode("PD220022");
    workStationTwo.setDescription("Linux Working Station");
    workStationTwo.setOfficeRoom(officeRoomJava);

    if (!workStationService.checkIfWorkStationExist(workStationTwo)) {
      this.workStationService.save(workStationTwo);
    }

    WorkStation workStationThree = new WorkStation();
    workStationThree.setCode("PD330033");
    workStationThree.setDescription("Windows Working Station");
    workStationThree.setOfficeRoom(officeRoomQa);

    if (!workStationService.checkIfWorkStationExist(workStationThree)) {
      this.workStationService.save(workStationThree);
    }

    WorkStation workStationFour = new WorkStation();
    workStationFour.setCode("PD440044");
    workStationFour.setDescription("Linux Working Station");
    workStationFour.setOfficeRoom(officeRoomQa);

    if (!workStationService.checkIfWorkStationExist(workStationFour)) {
      this.workStationService.save(workStationFour);
    }

    User userMarko = new User();
    userMarko.setName("Marko Ilic");
    userMarko.setEmail("marko.ilic@prodyna.com");

    if (!userService.checkIfUserExist(userMarko)) {
      this.userService.save(userMarko);
    }

    User userMiroslav = new User();
    userMiroslav.setName("Miroslav Perovic");
    userMiroslav.setEmail("miroslav.perovic@prodyna.com");

    if (!userService.checkIfUserExist(userMiroslav)) {
      this.userService.save(userMiroslav);
    }

    Reservation reservationOne = new Reservation();
    String dateOne = "2021-12-30";
    LocalDate parseDateOne = LocalDate.parse(dateOne);
    reservationOne.setDate(parseDateOne);
    User userOne = userRepository.findUserByEmail("marko.ilic@prodyna.com");
    reservationOne.setUser(userOne);
    WorkStation workStationWindows = workStationRepository.findWorkStationByCode("PD110011");
    reservationOne.setWorkStation(workStationWindows);

    if (!reservationService.checkIfReservationExist(reservationOne)) {
      this.reservationService.save(reservationOne);
    }

    Reservation reservationTwo = new Reservation();
    String dateTwo = "2022-01-28";
    LocalDate parseDateTwo = LocalDate.parse(dateTwo);
    reservationTwo.setDate(parseDateTwo);
    User userTwo = userRepository.findUserByEmail("miroslav.perovic@prodyna.com");
    reservationTwo.setUser(userTwo);
    WorkStation workStationLinux = workStationRepository.findWorkStationByCode("PD440044");
    reservationTwo.setWorkStation(workStationLinux);

    if (!reservationService.checkIfReservationExist(reservationTwo)) {
      this.reservationService.save(reservationTwo);
    }
  }
}
