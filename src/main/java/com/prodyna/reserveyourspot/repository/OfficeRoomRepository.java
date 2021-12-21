package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRoomRepository extends JpaRepository<OfficeRoom, Integer> {

  @Query("select o from OfficeRoom o where o.officeSpace.id= :id")
  public List<OfficeRoom> findByOfficeSpaceId(@Param("id") int officeSpaceId);
}
