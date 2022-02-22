package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
  @Query("SELECT r FROM Reservation r WHERE r.workStation.id = :id AND r.date = :date")
  public Reservation findByDateAndWorkStationId(@Param("date") LocalDate date, @Param("id") int workStationId);

  @Query("SELECT r FROM Reservation r WHERE r.workStation.id = :workStationId AND r.date BETWEEN :dateFrom AND :dateTo")
  public List<Reservation> findReservationsByWorkStationIdAndDateRange(@Param("workStationId") int workStationId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  @Modifying
  @Query("DELETE FROM Reservation r WHERE r.date = :date AND r.user.id = :userId AND r.workStation.id = :workStationId")
  public void deleteByDateAndUserIdAndWorkStationId(@Param("date") LocalDate date, @Param("userId") int userId, @Param("workStationId") int workStationId);

  @Query("SELECT r FROM Reservation r WHERE r.date = :date AND r.user.id = :userId AND r.workStation.id = :workStationId")
  public Reservation findByDateAndUserIdAndWorkStationId(@Param("date") LocalDate date, @Param("userId") int userId, @Param("workStationId") int workStationId);
}
