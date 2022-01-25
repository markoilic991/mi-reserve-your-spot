package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

  User userMarko;
  User userStefan;
  User userIgor;
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
    userIgor = new User();
    userIgor.setName("Igor Marovic");
    userIgor.setEmail("igor.marovic@gmail.com");
  }

  @AfterEach
  public void cleanUp() {
    userRepository.deleteAll();
  }

  @Test
  public void should_Find_All_Users() throws Exception {
    Mockito.when(userService.findAll())
            .thenReturn((List<User>) Stream.of(userMarko, userStefan).collect(Collectors.toList()));

    mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  public void should_Find_User_By_Id() throws Exception {
    Mockito.when(userService.findById((int) anyInt())).thenReturn(userMarko);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
            .andDo(print())
            .andExpect(status().isOk());
  }

  @Test
  public void should_Add_New_User() throws Exception {
    Mockito.when(userService.save(any(User.class))).thenReturn(userIgor);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                    .content(objectMapper.writeValueAsString(userIgor))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Igor Marovic"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("igor.marovic@gmail.com"));
  }

  @Test
  public void when_User_is_Invalid_Then_Return_Status400() throws Exception {
    userStefan.setEmail("asdasdasfadasasd");
    String body = objectMapper.writeValueAsString(userStefan);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType("application/json").content(body))
            .andExpect(status().isCreated());
  }

  @Test
  public void should_Update_User() {
    userUpdated = new User();
    userUpdated.setName("Milos Radin");
    userUpdated.setEmail(userMarko.getEmail());

    Mockito.when(userService.findById(userMarko.getId())).thenReturn(userUpdated);
  }
}
