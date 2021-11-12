package com.pluralsight.reserve_your_spot.repository;

import com.pluralsight.reserve_your_spot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {




}
