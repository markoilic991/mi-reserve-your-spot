package com.myproject.ReserveYourSpot.service;

import com.myproject.ReserveYourSpot.repository.ReservartionRepository;
import com.myproject.ReserveYourSpot.repository.UserRepository;
import com.myproject.ReserveYourSpot.exception.ReservationAlreadyExistException;
import com.myproject.ReserveYourSpot.exception.UserAlreadyReservedWorkStationException;
import com.myproject.ReserveYourSpot.exception.WorkStationBusyException;
import com.myproject.ReserveYourSpot.model.Reservation;
import com.myproject.ReserveYourSpot.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Validated
public class ReservationService {

    private ReservartionRepository reservartionRepository;
    private UserRepository userRepository;
    private WorkStationRepository workStationRepository;


    @Autowired
    public ReservationService(ReservartionRepository reservartionRepository, UserRepository userRepository, WorkStationRepository workStationRepository) {
        this.reservartionRepository = reservartionRepository;
        this.userRepository = userRepository;
        this.workStationRepository = workStationRepository;
    }


    public Reservation addReservation(Reservation reservation) {
        checkIfReservationExistUsingStream(reservation);
        return reservartionRepository.save(reservation);
    }

    public List<Reservation> saveAll(List<Reservation> revs){
        return reservartionRepository.saveAll(revs);
    }


    public List<Reservation> getAll() {
        List<Reservation> reservations = reservartionRepository.findAll();
        return reservations;
    }

    public Reservation getById(int id) {
        return reservartionRepository.findById(id).orElse(null);
    }

    public String deleteById(int id){
        reservartionRepository.deleteById((int) id);
        return "Reservations deleted!";
    }

    public void checkIfReservationExistUsingStream(Reservation reservation) {

        int userId = reservation.getUser().getId();
        String pattern = "yyyy-MM-dd";
        LocalDate date = reservation.getDate();
        String date1 = date.format(DateTimeFormatter.ofPattern(pattern));
        int workStationId = reservation.getWorkStation().getId();

        List<Reservation> reservations = reservartionRepository.findAll();

        reservations.stream()
                .filter(temp -> {
                    if (temp.getDate().toString().equals(date1) && temp.getUser().getId() == userId && temp.getWorkStation().getId() == workStationId) {

                        throw new ReservationAlreadyExistException("Reservation already exist in database!");

                    }
                    if (temp.getDate().toString().equals(date1) && temp.getUser().getId() != userId && temp.getWorkStation().getId() == workStationId){

                        throw new WorkStationBusyException("WorkStation busy! Choose another workStation for this date!");

                    }

                    if (temp.getDate().toString().equals(date1) && temp.getUser().getId() == userId && temp.getWorkStation().getId() != workStationId){

                        throw new UserAlreadyReservedWorkStationException("User with id: " + userId + " has already reserved another workStation for this date! Only one workStation can be reserved by day!");
                    }
                    return true;
                })
                .forEach(System.out::println);

    }
}

