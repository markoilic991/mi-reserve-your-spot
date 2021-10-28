package com.pluralsight.reserve_your_spot.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.reserve_your_spot.model.Response;
import com.pluralsight.reserve_your_spot.model.User;
import com.pluralsight.reserve_your_spot.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private User user;

    @Test
    public void testGetAllUsers() throws Exception {

        List<User> usersNewList = new ArrayList<>();
        usersNewList.add(new User(1, "Marko", "marko.ilic@prodyna"));
        usersNewList.add(new User(2, "Milos", "milos.ilic@prodyna"));
        usersNewList.add(new User(3, "Sloba", "sloba.ilic@prodyna"));

        Mockito.when(userService.getUsers()).thenReturn(usersNewList);

        mockMvc.perform(get("/users/getAll")).andDo(print()).andExpect(status().isOk());
    }

}
