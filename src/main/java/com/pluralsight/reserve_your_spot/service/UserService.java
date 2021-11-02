package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.exception.NameNotValidException;
import com.pluralsight.reserve_your_spot.exception.UserNotFoundException;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
@Validated
public class UserService {

    private UserRepository userRepository;

    private Validator validator;

    public UserService(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //add user
    public User addUser(@Valid User user){
        if(user.getName().equals("")){
          throw new NameNotValidException("The name of user is not valid, must have an value!");
        }
        return userRepository.save(user);
    }

    //get user
    public List<User>getUsers() {
        return userRepository.findAll();
    }

    //get by Id
    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
    }

    //delete user
    public String deleteById(int id){
        userRepository.deleteById(id);
        return "User deleted!";
    }

    //update User
    public User updateUser(User user){
        User oldUser = userRepository.findById(user.getId()).orElse(null);
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        return userRepository.save(oldUser);
    }

    public void validateUser(@Valid User user){
        System.out.println("User is validated!");
    }

}
