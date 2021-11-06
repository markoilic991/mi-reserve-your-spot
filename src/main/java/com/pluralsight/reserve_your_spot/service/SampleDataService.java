package com.pluralsight.reserve_your_spot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;

@Service
public class SampleDataService {

    private UserService userService;
    @Autowired
    private OfficeRoomService officeRoomService;
    @Autowired
    private WorkStationService workStationService;

    @Autowired
    public SampleDataService(UserService userService) {
        this.userService = userService;
    }

    public SampleDataService() {

    }

    @Bean
    CommandLineRunner runnerUser(UserService userService) {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
            try {
                List<User> users = mapper.readValue(inputStream, typeReference);
                userService.saveAll(users);
                System.out.println("Users Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save users: " + e.getMessage());
            }
        };

    }

    @Bean
    CommandLineRunner runnerRoom(OfficeRoomService officeRoomService) {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<OfficeRoom>> typeReference = new TypeReference<List<OfficeRoom>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/rooms.json");
            try {
                List<OfficeRoom> rooms = mapper.readValue(inputStream, typeReference);
                officeRoomService.saveAll(rooms);
                System.out.println("Rooms Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save rooms: " + e.getMessage());
            }
        };

    }

    @Bean
    CommandLineRunner runnerStation(WorkStationService workStationService) {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<WorkStation>> typeReference = new TypeReference<List<WorkStation>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/stations.json");
            try {
                List<WorkStation> stations = mapper.readValue(inputStream, typeReference);
                workStationService.saveAll(stations);
                System.out.println("Stations Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save stations: " + e.getMessage());
            }
        };

    }

}




