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

  @Query("select r from Reservation r JOIN r.user u where u.id=:id and r.date=:date")
  public Reservation findByDateAndUserId(@Param("date") LocalDate date, @Param("id") int userId);

  @Query("select r from Reservation r JOIN r.workStation w where w.id=:id and r.date=:date")
  public Reservation findByDateAndWorkStationId(@Param("date") LocalDate date, @Param("id") int workStationId);

  @Query("select r from Reservation r where r.date BETWEEN :dateFrom AND :dateTo")
  public List<Reservation> findAllReservationByDateRange(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  @Query(value = "select * from reservations where user_id=:id and work_station_id=:id and date=:date", nativeQuery = true)
  public Reservation findByUserIdAndWorkStationIdAndDate(@Param("date") LocalDate date, @Param("id") int userId, @Param("id") int workStationId);

}


