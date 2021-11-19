package com.prodyna.reserveyourspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.reserveyourspot.model.User;
import com.prodyna.reserveyourspot.repository.UserRepository;
import com.prodyna.reserveyourspot.service.UserService;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private User user;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void should_Find_All_Users() throws Exception {

    List<User> usersNewList = new ArrayList<>();
    usersNewList.add(new User(1, "Marko", "marko.ilic@prodyna"));
    usersNewList.add(new User(2, "Milos", "milos.ilic@prodyna"));
    usersNewList.add(new User(3, "Sloba", "sloba.ilic@prodyna"));

    Mockito.when(userService.findAll()).thenReturn(usersNewList);

    mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void should_Find_User_By_Id() throws Exception {

    User userNew = new User(1, "Marko", "marko.ilic@prodyna");
    userNew.setName("Ilija Milic");
    userNew.setEmail("ilija.ilic@prodyna.com");

    Mockito.when(userService.findById((int) anyInt())).thenReturn(java.util.Optional.of(userNew));

    mockMvc.perform(MockMvcRequestBuilders.get("/users/2"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ilija Milic"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ilija.ilic@prodyna.com"))
            .andExpect(status().isOk());
  }


  @Test
  public void should_Add_New_User() throws Exception {

    User userNew = new User(1, "Marko", "marko.ilic@prodyna");
    userNew.setId(1);
    userNew.setName("Ilija Milic");
    userNew.setEmail("ilija.ilic@prodyna.com");

    Mockito.when(userService.save(any(User.class))).thenReturn(userNew);

    mockMvc.perform(MockMvcRequestBuilders.post("/users/")
                    .content(objectMapper.writeValueAsString(userNew))
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ilija Milic"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ilija.ilic@prodyna.com"));
  }

  @Test
  public void when_User_is_Invalid_Then_Return_Status400() throws Exception {

    User userNew = new User("", "asdadadasdas");
    String body = objectMapper.writeValueAsString(userNew);

    mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType("application/json").content(body))
            .andExpect(status().isBadRequest());

  }

}
