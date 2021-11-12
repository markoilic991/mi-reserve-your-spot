package com.pluralsight.reserve_your_spot.repository;

import com.pluralsight.reserve_your_spot.model.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkStationRepository extends JpaRepository<WorkStation, Integer> {



}
