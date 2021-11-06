package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.model.Reservation;
import com.pluralsight.reserve_your_spot.repository.ReservartionRepository;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class ReservationService {

    private ReservartionRepository reservartionRepository;
    private UserRepository userRepository;


    @Autowired
    public ReservationService(ReservartionRepository reservartionRepository) {
        this.reservartionRepository = reservartionRepository;
    }

    public Reservation addReservation(Reservation reservation){
            return reservartionRepository.save(reservation);
    }

    public List<Reservation> getAll(){
        List<Reservation> reservations = reservartionRepository.findAll();
        return reservations;
    }
}
