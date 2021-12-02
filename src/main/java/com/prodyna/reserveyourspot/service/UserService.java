package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public List<User> saveAll(List<User> users) {
    return userRepository.saveAll(users);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findById(int id) {
    return userRepository.findById((int) id);
  }

  public String deleteById(int id) {
    userRepository.deleteById((int) id);
    return "User deleted!";
  }

  public void validateUser(@Valid User user) {
    System.out.println("User is validated!");
  }

}
