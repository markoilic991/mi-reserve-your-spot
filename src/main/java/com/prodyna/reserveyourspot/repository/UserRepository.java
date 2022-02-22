package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  public User findByEmail(String email);
}
