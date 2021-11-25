package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeSpaceRepository extends JpaRepository<OfficeSpace, Integer> {

  @Query("select os from OfficeSpace os where id=:id")
  public OfficeSpace findOfficeSpaceById(@Param("id") int id);
}
