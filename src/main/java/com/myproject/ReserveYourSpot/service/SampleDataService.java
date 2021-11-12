package com.myproject.ReserveYourSpot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.ReserveYourSpot.model.User;
import com.myproject.ReserveYourSpot.model.WorkStation;
import com.myproject.ReserveYourSpot.model.OfficeRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Component
public class SampleDataService {

    private static final Logger logger = LogManager.getLogger(SampleDataService.class.getName());

    private UserService userService;

    private OfficeRoomService officeRoomService;

    private WorkStationService workStationService;


    @Autowired
    public SampleDataService(UserService userService, OfficeRoomService officeRoomService, WorkStationService workStationService){
        this.userService = userService;
        this.officeRoomService = officeRoomService;
        this.workStationService = workStationService;
    }

    private final Type USER_TYPE = new TypeToken<List<User>>() {
    }.getType();

    private final Type STATION_TYPE = new TypeToken<List<WorkStation>>() {
    }.getType();

    private final Type ROOM_TYPE = new TypeToken<List<OfficeRoom>>() {
    }.getType();


    @PostConstruct
    public List<User> readUsersJsonFile() throws FileNotFoundException, UnsupportedEncodingException {

        Gson gson = new Gson();

        ClassLoader classLoader = gson.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("json/users.json");

        List<User> users = gson.fromJson(new InputStreamReader(in, "UTF-8"),USER_TYPE);
        System.out.println(Arrays.asList(users));

        userService.saveAll(users);
        logger.info("Users saved successfully!");
        return users;
    }

    @PostConstruct
    public List<WorkStation> readStationsJsonFile() throws FileNotFoundException, UnsupportedEncodingException {

        Gson gson = new Gson();

        ClassLoader classLoader = gson.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("json/stations.json");

        List<WorkStation> stations = gson.fromJson(new InputStreamReader(in, "UTF-8"),STATION_TYPE);
        System.out.println(Arrays.asList(stations));

        workStationService.saveAll(stations);
        logger.info("WorkStations saved successfully!");
        return stations;
    }

    @PostConstruct
    public List<OfficeRoom> readRoomsJsonFile() throws FileNotFoundException, UnsupportedEncodingException {

        Gson gson = new Gson();

        ClassLoader classLoader = gson.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("json/rooms.json");

        List<OfficeRoom> rooms = gson.fromJson(new InputStreamReader(in, "UTF-8"),ROOM_TYPE);
        System.out.println(Arrays.asList(rooms));

        officeRoomService.saveAll(rooms);
        logger.info("OfficeRooms saved successfully!");
        return rooms;
    }


}




