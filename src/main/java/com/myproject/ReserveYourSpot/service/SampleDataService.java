package com.myproject.ReserveYourSpot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.ReserveYourSpot.model.User;
import com.myproject.ReserveYourSpot.model.WorkStation;
import com.myproject.ReserveYourSpot.model.OfficeRoom;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
@Log
@Component
public class SampleDataService {

    private UserService userService;

    private OfficeRoomService officeRoomService;

    private WorkStationService workStationService;


    @Autowired
    // Advice: very good (autowiring with constructor), this can be covered with lombok annotations like @AllArgsConstructor
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
    // Comment: if we are just throwing FNFE who will catch it? how to notify developers of such an exception?
    public List<User> readUsersJsonFile() throws FileNotFoundException, UnsupportedEncodingException {

        Gson gson = new Gson();

        ClassLoader classLoader = gson.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("json/users.json");

        List<User> users = gson.fromJson(new InputStreamReader(in, "UTF-8"),USER_TYPE);
        System.out.println(Arrays.asList(users)); // Comment: should use logging instead

        userService.saveAll(users);
        log.info("Users saved successfully!");
        return users;
    }

    @PostConstruct
    public List<WorkStation> readStationsJsonFile() throws FileNotFoundException, UnsupportedEncodingException {

        Gson gson = new Gson();

        ClassLoader classLoader = gson.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("json/stations.json");

        List<WorkStation> stations = gson.fromJson(new InputStreamReader(in, "UTF-8"),STATION_TYPE);
        System.out.println(Arrays.asList(stations)); // Comment: should use logging instead

        workStationService.saveAll(stations);
        log.info("WorkStations saved successfully!");
        return stations;
    }

    @PostConstruct
    public List<OfficeRoom> readRoomsJsonFile() throws FileNotFoundException, UnsupportedEncodingException {

        Gson gson = new Gson();

        ClassLoader classLoader = gson.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("json/rooms.json");

        List<OfficeRoom> rooms = gson.fromJson(new InputStreamReader(in, "UTF-8"),ROOM_TYPE);
        System.out.println(Arrays.asList(rooms)); // Comment: should use logging instead

        officeRoomService.saveAll(rooms);
        log.info("OfficeRooms saved successfully!");
        return rooms;
    }


}




