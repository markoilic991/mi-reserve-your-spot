package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRoomRepository extends JpaRepository<OfficeRoom, Integer> {
  @Query("SELECT o FROM OfficeRoom o WHERE o.officeSpace.id= :id")
  public List<OfficeRoom> findByOfficeSpaceId(@Param("id") int officeSpaceId);

  @Query("SELECT o FROM OfficeRoom o WHERE o.code = :code")
  public OfficeRoom findOfficeRoomByCode(@Param("code") int code);
}
