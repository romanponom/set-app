package com.integration;

import com.db.DBConnectionApplication;
import com.db.controller.DBRestController;
import com.db.entity.User;
import com.db.repository.UserRepository;
import com.db.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ContextConfiguration(classes = DBConnectionApplication.class)
@WebMvcTest(controllers = DBRestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class DbServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private UserService userService;

    private final List<User> users = new ArrayList<>();
    private final User user = new User(2, "qqqq", "eeee@ww.ee", false);

    @BeforeAll
    public void setUp() {
        users.add(new User(1, "admin", "admin@ww.ee", true));
        users.add(user);
    }

    @Test
    @DisplayName("Get all users")
    public void getAllUsersTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(users);

        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/api/all")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    @DisplayName("Get user")
    public void getUserTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(user);

        when(userService.getUser((long) 2)).thenReturn(user);
        mockMvc.perform(get("/api/user/" + user.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("Add user")
    public void addUserTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(user);
        user.setValidated(false);
        user.setId(1);
        String response = mapper.writeValueAsString(user);

        when(userService.addOrUpdateUser(any(User.class))).thenReturn(user);

        mockMvc.perform(
                post("/api/add-user")
                .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(response));
    }

    @Test
    public void deleteUserTest() throws Exception {
        user.setValidated(false);
        user.setId(1);
        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(user);

        //mockMvc.perform(
        //        delete("/api/delete-user/" + user.getId())
        //)
    }

}
