package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  @Query("select r from Reservation r JOIN FETCH r.user u where u.id=:id and r.date=:date")
  public Reservation findByDateAndUserId(@Param("date") LocalDate date, @Param("id") int userId);

  @Query("select r from Reservation r JOIN FETCH r.workStation w where w.id=:id and r.date=:date")
  public Reservation findByDateAndWorkStationId(@Param("date") LocalDate date, @Param("id") int workStationId);

  @Query("select r from Reservation r where r.date BETWEEN :dateFrom AND :dateTo")
  public List<Reservation> findAllReservationByDateRange(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("delete from Reservation r where r.date = :date and r.user.id = :userId and r.workStation.id = :workStationId")
  public void deleteByDateAndUserIdAndWorkStationId(@Param("date") LocalDate date, @Param("userId") int userId, @Param("workStationId") int workStationId);

}


