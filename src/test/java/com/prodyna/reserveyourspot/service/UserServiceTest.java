package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @MockBean
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private User user;

  User newUser;
  User newUser1;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    newUser = new User();
    newUser.setId(1);
    newUser.setName("Marko Ilic");
    newUser.setEmail("marko.ilic@prodyna.com");
    newUser1 = new User();
    newUser1.setId(2);
    newUser1.setName("Stefan Markovic");
    newUser1.setEmail("stefan.markovic@gmail.com");

  }

  @AfterEach
  public void cleanUp() {

    userRepository.findAll();
    userRepository.deleteAll();

  }

  @Test
  public void should_Get_All_Users() {

    Mockito.when(userRepository.findAll())
            .thenReturn((List<User>) Stream.of(newUser, newUser1).collect(Collectors.toList()));

    Assertions.assertEquals(2, userService.findAll().size());

  }

  @Test
  public void should_Get_User_By_Id() {

    Mockito.when(userRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(newUser));

    User user = userService.findById(1);

    Assertions.assertNotNull(user);
    Assertions.assertEquals("Marko Ilic", user.getName());

  }

  @Test
  public void should_Delete_User_By_Id() {

    userService.deleteById(newUser.getId());

    Mockito.verify(userRepository, Mockito.times(1)).deleteById(newUser.getId());

  }

  @Test
  public void when_User_Is_Invalid_Then_Throws_Exception() {

    newUser.setEmail("asdasdasdasdasdasdasd");

    Assertions.assertThrows(ConstraintViolationException.class, () -> {

      userService.validateUser(newUser);
    });

  }

}
