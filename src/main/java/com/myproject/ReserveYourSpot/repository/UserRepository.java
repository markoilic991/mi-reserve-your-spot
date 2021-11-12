package com.myproject.ReserveYourSpot.repository;

import com.myproject.ReserveYourSpot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {




}