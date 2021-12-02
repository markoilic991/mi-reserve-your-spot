package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeSpaceRepository extends JpaRepository<OfficeSpace, Integer> {
}
