package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public User save(@Valid @RequestBody User user) {
    return userService.save(user);
  }

  @PostMapping("/list")
  public List<User> saveAll(@RequestBody List<User> users) {
    return userService.saveAll(users);
  }

  @GetMapping("/")
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public User findById(@PathVariable int id) {
    return userService.findById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable int id) {
    userService.deleteById(id);
  }

}
