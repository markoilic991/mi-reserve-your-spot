package com.prodyna.reserveyourspot.controller;

import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public User findById(@PathVariable int id) {
    return userService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User save(@RequestBody User user) {
    return userService.save(user);
  }

  @PostMapping("/list")
  @ResponseStatus(HttpStatus.CREATED)
  public List<User> saveAll(@RequestBody List<User> users) {
    return userService.saveAll(users);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteById(@PathVariable int id) {
    userService.deleteById(id);
    return "User deleted successfully!";
  }

  @PutMapping("/{id}")
  public User update(@RequestBody User user, @PathVariable int id) {
    return userService.updateUser(user, id);
  }
}
