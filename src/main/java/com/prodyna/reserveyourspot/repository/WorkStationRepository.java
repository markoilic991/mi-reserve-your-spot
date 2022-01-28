package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkStationRepository extends JpaRepository<WorkStation, Integer> {
  @Query("SELECT w FROM WorkStation w WHERE w.officeRoom.id = :id")
  public List<WorkStation> findByOfficeRoomId(@Param("id") int id);

  @Query("SELECT w FROM WorkStation w WHERE w.code = :code")
  public WorkStation findWorkStationByCode(@Param("code") String code);
}
