package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRoomRepository extends JpaRepository<OfficeRoom, Integer> {
}
