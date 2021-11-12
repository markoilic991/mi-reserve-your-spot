package com.myproject.ReserveYourSpot.controller;

import com.myproject.ReserveYourSpot.model.User;
import com.myproject.ReserveYourSpot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private List<User> listUsers = new ArrayList<>();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/list")
    public List<User> listOfUsers(@RequestBody List<User> users){
        return userService.saveAll(users);
    }

    @GetMapping("/")
    public List<User> getUsers() {
            return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
       userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id){
        return userService.updateUser(user,id);

    }



}
