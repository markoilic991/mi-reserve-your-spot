package com.myproject.ReserveYourSpot.controller;

import com.myproject.ReserveYourSpot.model.User;
import com.myproject.ReserveYourSpot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private List<User> listUsers = new ArrayList<>(); // Comment: not set, can be removed

    @Autowired
    // Advice: very good (autowiring with constructor), this can be covered with lombok annotations like @AllArgsConstructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    // Comment: naming
    public User addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/list")
    // Comment: naming
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
    // Comment: why separate id? why not @Valid?
    public User updateUser(@RequestBody User user, @PathVariable int id){
        return userService.updateUser(user,id);

    }



}
