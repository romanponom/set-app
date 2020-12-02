package com.component;

import com.db.DBConnectionApplication;
import com.db.controller.DBRestController;
import com.db.entity.User;
import com.db.entity.ValidatedUser;
import com.db.repository.UserRepository;
import com.db.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ContextConfiguration(classes = DBConnectionApplication.class)
@WebMvcTest(controllers = DBRestController.class)
@ExtendWith(MockitoExtension.class)
public class DbRestControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @MockBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();
    private List<User> users;
    private User secondNotValidatedUser;
    private User firstValidatedUser;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        firstValidatedUser = new User(1, "admin", "admin@ww.ee", true);
        secondNotValidatedUser = new User(2, "qqqq", "eeee@ww.ee", false);
        users.add(firstValidatedUser);
        users.add(secondNotValidatedUser);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Get all users")
    public void getAllUsersTest() throws Exception {
        String json = mapper.writeValueAsString(users);

        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/api/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Get user")
    public void getUserTest() throws Exception {
        String response = mapper.writeValueAsString(secondNotValidatedUser);

        when(userService.getUser((long) 2)).thenReturn(secondNotValidatedUser);
        mockMvc.perform(get("/api/user/" + secondNotValidatedUser.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Add user")
    public void addUserTest() throws Exception {
        String request = mapper.writeValueAsString(secondNotValidatedUser);

        when(userService.addOrUpdateUser(any(User.class))).thenReturn(secondNotValidatedUser);

        mockMvc.perform(
                post("/api/add-user")
                .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(request));
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Update user")
    public void updateUserTest() throws Exception {
        User updatedUser = new User(firstValidatedUser.getId(),
                firstValidatedUser.getName(), "qqqq@rr.ww", false);
        String request = mapper.writeValueAsString(updatedUser);
        String response = mapper.writeValueAsString(updatedUser);

        when(userService.getUser(firstValidatedUser.getId())).thenReturn(firstValidatedUser);
        when(userService.addOrUpdateUser(updatedUser)).thenReturn(updatedUser);

        mockMvc.perform(
                post("/api/update-user/" + firstValidatedUser.getId())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Delete user")
    public void deleteUserTest() throws Exception {
        String response = mapper.writeValueAsString(firstValidatedUser);

        when(userService.getUser(firstValidatedUser.getId())).thenReturn(firstValidatedUser);
        mockMvc.perform(
                delete("/api/delete-user/" + firstValidatedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Delete all users")
    public void deleteAllUsersTest() throws Exception {
        String response = mapper.writeValueAsString(users);

        when(userService.deleteAllUsers()).thenReturn(users);

        mockMvc.perform(
                delete("/api/delete-all-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Validate user")
    public void validateUserTest() throws Exception {
        ValidatedUser validatedUser = new ValidatedUser(true);
        String request = mapper.writeValueAsString(validatedUser);
        User responseUser = new User(secondNotValidatedUser.getId(),
                secondNotValidatedUser.getName(), secondNotValidatedUser.getEmail(), true);
        String response = mapper.writeValueAsString(responseUser);

        when(userService.getUser(secondNotValidatedUser.getId())).thenReturn(secondNotValidatedUser);
        when(userService.addOrUpdateUser(responseUser)).thenReturn(responseUser);

        mockMvc.perform(
                patch("/api/validate-user/" + secondNotValidatedUser.getId())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }
}
