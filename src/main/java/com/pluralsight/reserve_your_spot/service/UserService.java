package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.exception.NameNotValidException;
import com.pluralsight.reserve_your_spot.exception.UserNotFoundException;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.*;
import java.util.List;


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

    public User addUser(@Valid User user){
        if(user.getName().equals("")){
          throw new NameNotValidException("The name of user is not valid, must have an value!");
        }
        return userRepository.save(user);
    }

    public List<User> saveAll(List<User> users){
        return userRepository.saveAll(users);
    }

    public List<User>getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById((int) id).orElseThrow(()-> new UserNotFoundException("User not found with id: " + id));
    }

    public String deleteById(int id){
        userRepository.deleteById((int) id);
        return "User deleted!";
    }

    public User updateUser(User user, int id){
        User oldUser = userRepository.findById((int) id).orElse(null);
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        return userRepository.save(oldUser);
    }

    public void validateUser(@Valid User user){
        System.out.println("User is validated!");
    }


}
