package com.prodyna.reserveyourspot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(UserService.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  @Autowired
  private UserRepository userRepository;

  @MockBean
  private User user;

  User userMarko;
  User userStefan;
  User userUpdated;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    userMarko = new User();
    userMarko.setId(1);
    userMarko.setName("Marko Ilic");
    userMarko.setEmail("marko.ilic@prodyna.com");
    userStefan = new User();
    userStefan.setId(2);
    userStefan.setName("Stefan Markovic");
    userStefan.setEmail("stefan.markovic@gmail.com");
  }

  @AfterEach
  public void cleanUp() {
    userRepository.deleteAll();
  }

  @Test
  public void should_Get_All_Users() {
    Mockito.when(userRepository.findAll())
            .thenReturn((List<User>) Stream.of(userMarko, userStefan).collect(Collectors.toList()));

    Assertions.assertEquals(2, userService.findAll().size());
  }

  @Test
  public void should_Get_User_By_Id() {
    Mockito.when(userRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(userMarko));
    User user = userService.findById(1);

    Assertions.assertNotNull(user);
    Assertions.assertEquals("Marko Ilic", user.getName());
  }

  @Test
  public void should_Delete_User_By_Id() {
    Mockito.when(userRepository.findById((int) anyInt())).thenReturn(Optional.ofNullable(userStefan));
    userService.deleteById(userStefan.getId());
    Mockito.verify(userRepository, Mockito.times(1)).deleteById(userStefan.getId());
  }

  @Test
  public void should_Update_User() {
    userUpdated = new User();
    userUpdated.setName("Momir Ilic");
    userUpdated.setEmail(userStefan.getEmail());

    Mockito.when(userRepository.findById(userStefan.getId())).thenReturn(Optional.of(userUpdated));
  }
}
