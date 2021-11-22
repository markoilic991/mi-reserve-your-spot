package com.prodyna.reserveyourspot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prodyna.reserveyourspot.model.OfficeRoom;
import com.prodyna.reserveyourspot.model.OfficeSpace;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.model.WorkStation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class SampleDataService {

  private UserService userService;

  private OfficeRoomService officeRoomService;

  private WorkStationService workStationService;

  private OfficeSpaceService officeSpaceService;

  @Autowired
  public SampleDataService(UserService userService, OfficeRoomService officeRoomService, WorkStationService workStationService, OfficeSpaceService officeSpaceService) {
    this.userService = userService;
    this.officeRoomService = officeRoomService;
    this.workStationService = workStationService;
    this.officeSpaceService = officeSpaceService;
  }

  private final Type USER_TYPE = new TypeToken<List<User>>() {
  }.getType();

  private final Type STATION_TYPE = new TypeToken<List<WorkStation>>() {
  }.getType();

  private final Type ROOM_TYPE = new TypeToken<List<OfficeRoom>>() {
  }.getType();

  private final Type SPACE_TYPE = new TypeToken<List<OfficeSpace>>() {
  }.getType();

  @PostConstruct
  public List<User> readUsersJsonFile() {

    log.debug("Reading json file and pushing data to mysql database!");
    Gson gson = new Gson();

    ClassLoader classLoader = gson.getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("json/users.json");

    List<User> users = null;

    try {
      users = gson.fromJson(new InputStreamReader(in, "UTF-8"), USER_TYPE);

    } catch (UnsupportedEncodingException e) {

      e.printStackTrace();
    }

    userService.saveAll(users);
    log.info("Users saved successfully!");
    return users;
  }

  @PostConstruct
  public List<WorkStation> readStationsJsonFile() {

    log.debug("Reading json file and pushing data to mysql database!");
    Gson gson = new Gson();

    ClassLoader classLoader = gson.getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("json/workStations.json");

    List<WorkStation> stations = null;
    try {
      stations = gson.fromJson(new InputStreamReader(in, "UTF-8"), STATION_TYPE);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    workStationService.saveAll(stations);
    log.info("WorkStations saved successfully!");
    return stations;
  }

  @PostConstruct
  public List<OfficeRoom> readRoomsJsonFile() {

    log.debug("Reading json file and pushing data to mysql database!");
    Gson gson = new Gson();

    ClassLoader classLoader = gson.getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("json/officeRooms.json");

    List<OfficeRoom> rooms = null;
    try {
      rooms = gson.fromJson(new InputStreamReader(in, "UTF-8"), ROOM_TYPE);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    officeRoomService.saveAll(rooms);
    log.info("OfficeRooms saved successfully!");
    return rooms;
  }

  @PostConstruct
  public List<OfficeSpace> readSpaceJsonFile() {

    log.debug("Reading json file and pushing data to mysql database!");
    Gson gson = new Gson();

    ClassLoader classLoader = gson.getClass().getClassLoader();
    InputStream in = classLoader.getResourceAsStream("json/officeSpace.json");

    List<OfficeSpace> officeSpaces = null;
    try {
      officeSpaces = gson.fromJson(new InputStreamReader(in, "UTF-8"), SPACE_TYPE);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    officeSpaceService.saveAll(officeSpaces);
    log.info("OfficeSpace saved successfully!");
    return officeSpaces;
  }

}




