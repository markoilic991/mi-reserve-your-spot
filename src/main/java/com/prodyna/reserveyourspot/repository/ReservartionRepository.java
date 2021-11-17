package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservartionRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "select * from reservations r where r.date=:date and r.work_station_id = :workStation_id", nativeQuery = true)
    public Reservation findReservationByDateAndByWorkStationId(@Param("date") LocalDate date, @Param("workStation_id") int workStationId);

    @Query(value = "select * from reservations r where r.date=:date and r.user_id = :user_id", nativeQuery = true)
    public Reservation findReservationByDateAndByUserId(@Param("date") LocalDate date, @Param("user_id") int userId);

    @Query(value = "select * from reservations r where r.date=:date and r.user_id=:user_id and r.work_station_id = :workStation_id", nativeQuery = true)
    public Reservation findReservationByDateAndUserIdAndWorkStationId(@Param("date") LocalDate date, @Param("user_id") int userId, @Param("workStation_id") int workStationId);

}
