package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OfficeSpaceRepository extends JpaRepository<OfficeSpace, Integer> {
  @Query("SELECT DISTINCT os FROM OfficeSpace os LEFT JOIN os.rooms r LEFT JOIN r.workStations ws LEFT JOIN ws.reservations rs ON rs.date BETWEEN :dateFrom AND :dateTo WHERE os.id=:id")
  @EntityGraph(value = "graph.officeSpace.OfficeRoom.WorkStations.Reservations")
  public OfficeSpace getOfficeSpaceByIdAndReservationDateRange(@Param("id") int id, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  public OfficeSpace findByName(String name);
}
