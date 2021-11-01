package com.pluralsight.reserve_your_spot.service;

import com.pluralsight.reserve_your_spot.exception.NameNotValidException;
import com.pluralsight.reserve_your_spot.exception.UserNotFoundException;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //add user
    public User addUser(User user){
        if(user.getName() == ""){
            throw new NameNotValidException("The name of user must have value!");
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
}
