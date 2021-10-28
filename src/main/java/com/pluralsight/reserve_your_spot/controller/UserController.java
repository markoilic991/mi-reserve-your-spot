package com.pluralsight.reserve_your_spot.controller;

import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //add one
    @PostMapping("/")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    // get users
    @GetMapping("/")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id){
        return userService.getUserById(id);
    }

    //delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteById(id);
    }

    //update User
    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);

    }
}
