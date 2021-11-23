package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservartionRepository extends JpaRepository<Reservation, Integer> {

  @Query("select r from Reservation r JOIN r.user u where u.id=:id and r.dateFrom=:dateFrom and r.dateTo=:dateTo")
  public Reservation findByDateFromAndDateToAndUserId(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("id") int userId);

  @Query("select r from Reservation r JOIN r.workStation w where w.id=:id and r.dateFrom=:dateFrom and r.dateTo=:dateTo")
  public Reservation findByDateFromAndDateToAndWorkStationId(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("id") int workStationId);

}


