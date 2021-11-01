package com.pluralsight.reserve_your_spot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.exception.UserNotFoundException;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.repository.UserRepository;
import com.pluralsight.reserve_your_spot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    @Test
    public void Should_Find_All_Users() throws Exception {

        List<User> usersNewList = new ArrayList<>();
        usersNewList.add(new User(1, "Marko", "marko.ilic@prodyna"));
        usersNewList.add(new User(2, "Milos", "milos.ilic@prodyna"));
        usersNewList.add(new User(3, "Sloba", "sloba.ilic@prodyna"));

        Mockito.when(userService.getUsers()).thenReturn(usersNewList);

        mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void Should_Find_User_By_Id() throws Exception {

        User userNew = new User();
        userNew.setName("Ilija Milic");
        userNew.setEmail("ilija.ilic@prodyna.com");

        Mockito.when(userService.getUserById(anyInt())).thenReturn(userNew);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/2"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ilija Milic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ilija.ilic@prodyna.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void Should_Get_User_By_Id(){

        User user1 = new User();
        user1.setName("Ivica");
        user1.setEmail("ivica@gmail");

        Mockito.when(userService.getUserById(anyInt())).thenReturn(user1);

        User user2 = userService.getUserById(1);

        Assertions.assertNotNull(user2);
        Assertions.assertEquals("Ivica", user2.getName());
    }

    @Test
    public void Should_Add_New_User() throws Exception {

        User userNew = new User();
        userNew.setId(1);
        userNew.setName("Ilija Milic");
        userNew.setEmail("ilija.ilic@prodyna.com");

        Mockito.when(userService.addUser(any(User.class))).thenReturn(userNew);

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
    public void Should_Update_User() throws Exception {

        User userNew = new User("Stefan", "stef.ilic@gmail.com");
        userNew.setName("Milos");
        userNew.setEmail("milos.radin@gmail.com");

        Mockito.when(userService.updateUser(any(User.class))).thenReturn(userNew);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                .content(objectMapper.writeValueAsString(userNew))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Milos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("milos.radin@gmail.com"));
    }



}
