package com.pluralsight.reserve_your_spot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(UserService.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private User user;

    @Test
    public void getUsersTest(){

        User newUser = new User(1, "Marko Ilic", "marko@prodyna.com");
        User newUser1 = new User(2, "Ivan Kotic", "ivan@gmail.com");

        Mockito.when(userRepository.findAll())
                .thenReturn((List<User>) Stream.of(newUser, newUser1).collect(Collectors.toList()));

        Assertions.assertEquals(2, userService.getUsers().size());
    }

    @Test
    public void getUserByIdTest(){

        User userNew = new User(1,"Marko Ilic", "marko.ilic@prodyna.com");
        userService.getUserById(userNew.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(userNew.getId());

    }

    @Test
    public void deleteByIdTest(){

        User userNew = new User(1,"Marko Ilic", "marko.ilic@prodyna.com");
        userService.deleteById(userNew.getId());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userNew.getId());

    }


}
