package com.myproject.ReserveYourSpot.service;

import com.myproject.ReserveYourSpot.model.User;
import com.myproject.ReserveYourSpot.repository.UserRepository;
import com.myproject.ReserveYourSpot.exception.NameNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.*;
import java.util.Arrays;
import java.util.List;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
@Service
@Validated
public class UserService {

    private UserRepository userRepository;

    // Comment: where do we use this validator
    private Validator validator;

    public UserService(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    // Advice: very good (autowiring with constructor), this can be covered with lombok annotations like @AllArgsConstructor
    // and you should chek if then you  could remove  public UserService(Validator validator) {
    //        this.validator = validator;
    //    }
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Comment: in case of naming we should use always the same rules like if there is saveAll, than saving (adding) one user should be just save
    // Comment: validate all field constraints in one place and only once
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
        return userRepository.findById((int) id).orElse(null);
    }

    public String deleteById(int id){
        userRepository.deleteById((int) id);
        return "User deleted!";
    }

    public User updateUser(User user, int id){
        User oldUser = userRepository.findById((int) id).orElse(null);
        // Comment: possible NullPointerException
        // Comment: could use lombok builder functionality here
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        return userRepository.save(oldUser);
    }

    public void validateUser(@Valid User user){
        System.out.println("User is validated!"); // Comment: should use logging instead
    }


}
