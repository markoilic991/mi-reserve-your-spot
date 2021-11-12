package com.myproject.ReserveYourSpot.repository;

import com.myproject.ReserveYourSpot.model.OfficeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRoomRepository extends JpaRepository<OfficeRoom, Integer> {
}
