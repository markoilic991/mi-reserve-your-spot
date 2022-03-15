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
  public Reservation findByDateAndWorkStationId(LocalDate date, int workStationId);

  @Query("SELECT r FROM Reservation r WHERE r.workStation.id = :workStationId AND r.date BETWEEN :dateFrom AND :dateTo")
  public List<Reservation> findReservationsByWorkStationIdAndDateRange(@Param("workStationId") int workStationId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.date BETWEEN :dateFrom AND :dateTo")
  public List<Reservation> findReservationsByUserIdAndDateRange(@Param("userId") int userId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  @Modifying
  @Query("DELETE FROM Reservation r WHERE r.date = :date AND r.user.id = :userId AND r.workStation.id = :workStationId")
  public void deleteByDateAndUserIdAndWorkStationId(@Param("date") LocalDate date, @Param("userId") int userId, @Param("workStationId") int workStationId);

  @Modifying
  @Query("DELETE FROM Reservation r WHERE r.id IN (:reservationsIds)")
  public void deleteAllReservations(@Param("reservationsIds") List<Integer> reservationsIds);

  @Query("SELECT r FROM Reservation r WHERE r.id IN (:reservationsIds)")
  public List<Reservation> findReservations(@Param("reservationsIds") List<Integer> reservationsIds);

  public Reservation findByDateAndUserIdAndWorkStationId(LocalDate date, int userId, int workStationId);
}
