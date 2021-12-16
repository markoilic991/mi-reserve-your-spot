package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.service.UserService;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private UserController userController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  @Autowired
  private UserService userService;

  @MockBean
  @Autowired
  private UserRepository userRepository;

  @MockBean
  private User user;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  User userMarko;
  User userStefan;

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
  public void should_Find_All_Users() throws Exception {

    Mockito.when(userService.findAll())
            .thenReturn((List<User>) Stream.of(userMarko, userStefan).collect(Collectors.toList()));

    mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void should_Find_User_By_Id() throws Exception {

    Mockito.when(userService.findById((int) anyInt())).thenReturn(userMarko);

    mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void should_Add_New_User() throws Exception {

    Mockito.when(userService.save(any(User.class))).thenReturn(userMarko);

    mockMvc.perform(MockMvcRequestBuilders.post("/users/")
                    .content(objectMapper.writeValueAsString(userMarko))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Marko Ilic"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("marko.ilic@prodyna.com"));
  }

  @Test
  public void when_User_is_Invalid_Then_Return_Status400() throws Exception {

    userStefan.setEmail("asdasdasfadasasd");
    String body = objectMapper.writeValueAsString(userStefan);

    mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());

  }

}
