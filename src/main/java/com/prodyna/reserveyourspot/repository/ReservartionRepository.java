package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservartionRepository extends JpaRepository<Reservation, Integer> {

  @Query(value = "select * from reservations r where r.date_from=:dateFrom and r.date_to=:dateTo and r.work_station_id = :workStation_id", nativeQuery = true)
  public Reservation findReservationByDateAndByWorkStationId(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("workStation_id") int workStationId);

  @Query(value = "select * from reservations r where r.date_from=:dateFrom and r.date_to=:dateTo and r.user_id = :user_id", nativeQuery = true)
  public Reservation findReservationByDateAndByUserId(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("user_id") int userId);

}


