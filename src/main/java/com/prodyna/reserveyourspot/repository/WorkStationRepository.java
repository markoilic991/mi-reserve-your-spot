package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkStationRepository extends JpaRepository<WorkStation, Integer> {
}
