package com.myproject.ReserveYourSpot.repository;

import com.myproject.ReserveYourSpot.model.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkStationRepository extends JpaRepository<WorkStation, Integer> {



}
