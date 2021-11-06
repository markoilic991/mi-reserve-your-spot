package com.pluralsight.reserve_your_spot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ExtendWith(SpringExtension.class)
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
    public void Should_Get_All_Users(){

        User newUser = new User(1, "Marko Ilic", "marko@prodyna.com");
        User newUser1 = new User(2, "Ivan Kotic", "ivan@gmail.com");

        Mockito.when(userRepository.findAll())
                .thenReturn((List<User>) Stream.of(newUser, newUser1).collect(Collectors.toList()));

        Assertions.assertEquals(2, userService.getUsers().size());
    }

    @Test
    public void Should_Get_User_By_Id(){

        User userNew = new User(1,"Marko Ilic", "marko.ilic@prodyna.com");
        userService.getUserById(userNew.getId());

        Mockito.verify(userRepository, Mockito.times(1)).getById(userNew.getId());

    }

    @Test
    public void Should_Delete_User_By_Id(){

        User userNew = new User(1,"Marko Ilic", "marko.ilic@prodyna.com");
        userService.deleteById(userNew.getId());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userNew.getId());

    }

    @Test
    public void when_User_Is_Invalid_Then_Throws_Exception(){

        User newUser = new User("", "dasdasdasdasd");

        Assertions.assertThrows(ConstraintViolationException.class, ()->{

            userService.validateUser(newUser);
        });
    }


}
