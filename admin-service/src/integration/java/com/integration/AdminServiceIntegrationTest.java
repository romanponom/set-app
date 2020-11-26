package com.integration;

import com.admin.client.DBClient;
import com.admin.client.ValidatorClient;
import com.admin.entity.User;
import com.admin.entity.ValidatedUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.config.DbServiceFeignConfig;
import com.integration.config.ValidatorServiceFeignConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {DbServiceFeignConfig.class, ValidatorServiceFeignConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminServiceIntegrationTest {

    @Autowired
    private DBClient dbClient;
    @Autowired
    private ValidatorClient validatorClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private List<User> users;
    private User secondNotValidatedUser;
    private User firstValidatedUser;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        firstValidatedUser = new User(1L, "admin", "admin@ww.qq", true);
        secondNotValidatedUser = new User(2L, "user", "user@ww.qq", false);
        users.add(firstValidatedUser);
        users.add(secondNotValidatedUser);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Get all users")
    public void getAllUsersTest() {
        assertThat(dbClient.getUsers()).isEqualTo(users);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Get user")
    public void getUserTest() {
        assertThat(dbClient.getUserById(firstValidatedUser.getId())).isEqualTo(firstValidatedUser);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Add user")
    public void addUserTest() {
        assertThat(dbClient.addUser(firstValidatedUser)).isEqualTo(firstValidatedUser);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Update user")
    public void updateUserTest() {
        User updatedUser = new User(firstValidatedUser.getId(),
                firstValidatedUser.getName(), firstValidatedUser.getEmail(), false);
        assertThat(dbClient.updateUser(firstValidatedUser, firstValidatedUser.getId())).isEqualTo(updatedUser);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Delete user")
    public void deleteUserTest() {
        assertThat(dbClient.deleteUser(firstValidatedUser.getId())).isEqualTo(firstValidatedUser);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Delete all users")
    public void deleteAllUserTest() {
        assertThat(dbClient.deleteAllUsers()).isEqualTo(users);
    }

    @Test
    @DisplayName("[INTEGRATION-TEST] Validate user")
    public void validateUserTest() {
        ValidatedUser validatedUser = new ValidatedUser(true);
        User user = new User(secondNotValidatedUser.getId(),
                secondNotValidatedUser.getName(), secondNotValidatedUser.getEmail(), validatedUser.isValidated());
        assertThat(validatorClient.validateUserById(secondNotValidatedUser.getId(), validatedUser)).isEqualTo(user);
    }
}
