package com.prodyna.reserveyourspot.service;

import com.prodyna.reserveyourspot.exception.EntityNotFoundException;
import com.prodyna.reserveyourspot.exception.UniqueValueException;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(int id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    throw new EntityNotFoundException("User with id " + id + " does not exist!");
  }

  public User findByEmail(String email){
    Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
    if (optionalUser.isPresent()){
      return optionalUser.get();
    }
    throw new EntityNotFoundException("User with email " + email + " does not exist!");
  }

  public User save(User user) {
    if (checkIfUserExist(user)) {
      throw new UniqueValueException("User has unique email! Try another one!");
    }
    return userRepository.save(user);
  }

  public String deleteById(int id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (!optionalUser.isPresent()) {
      throw new EntityNotFoundException("User with id " + id + " does not exist in database!");
    }
    userRepository.deleteById(id);
    return "User deleted successfully!";
  }

  public User updateUser(User user, int id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (!optionalUser.isPresent()) {
      throw new EntityNotFoundException("User with id " + id + " does not exist in database!");
    }
    User userUpdated = optionalUser.get();
    userUpdated.setName(user.getName());
    userUpdated.setEmail(user.getEmail());
    return userRepository.save(userUpdated);
  }

  public boolean checkIfUserExist(User user) {
    String email = user.getEmail();
    boolean userExist = false;
    Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

    if (optionalUser.isPresent()) {
      userExist = true;
    }
    return userExist;
  }
}
