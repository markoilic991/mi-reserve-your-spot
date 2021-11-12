package com.myproject.ReserveYourSpot.repository;

import com.myproject.ReserveYourSpot.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservartionRepository extends JpaRepository<Reservation, Integer> {


}
