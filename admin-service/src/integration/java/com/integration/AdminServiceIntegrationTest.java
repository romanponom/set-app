package com.integration;

import com.admin.client.DBClient;
import com.admin.client.ValidatorClient;
import com.admin.entity.User;
import com.admin.entity.ValidatedUser;
import com.integration.config.DbServiceFeignConfig;
import com.integration.config.ValidatorServiceFeignConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {DbServiceFeignConfig.class, ValidatorServiceFeignConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminServiceIntegrationTest {

    @Autowired
    private DBClient dbClient;
    @Autowired
    private ValidatorClient validatorClient;
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

    /*@Test
    @Order(4)
    @DisplayName("[INTEGRATION-TEST] Get all users")
    public void getAllUsersTest() {
        assertThat(dbClient.getUsers()).isEqualTo(users);
    }

    @Test
    @Order(5)
    @DisplayName("[INTEGRATION-TEST] Get user")
    public void getUserTest() {
        assertThat(dbClient.getUserById(firstValidatedUser.getId())).isEqualTo(firstValidatedUser);
    }*/

    @Test
    @Order(1)
    @DisplayName("[INTEGRATION-TEST] Add user")
    public void addUserTest() {
        assertThat(dbClient.addUser(secondNotValidatedUser)).isEqualTo(secondNotValidatedUser);
    }

    /*@Test
    @Order(2)
    @DisplayName("[INTEGRATION-TEST] Update user")
    public void updateUserTest() {
        User updatedUser = new User(firstValidatedUser.getId(),
                firstValidatedUser.getName(), firstValidatedUser.getEmail(), false);
        assertThat(dbClient.updateUser(firstValidatedUser, firstValidatedUser.getId())).isEqualTo(updatedUser);
    }

    @Test
    @Order(6)
    @DisplayName("[INTEGRATION-TEST] Delete user")
    public void deleteUserTest() {
        assertThat(dbClient.deleteUser(firstValidatedUser.getId())).isEqualTo(firstValidatedUser);
    }

    @Test
    @Order(7)
    @DisplayName("[INTEGRATION-TEST] Delete all users")
    public void deleteAllUserTest() {
        assertThat(dbClient.deleteAllUsers()).isEqualTo(users);
    }

    @Test
    @Order(3)
    @DisplayName("[INTEGRATION-TEST] Validate user")
    public void validateUserTest() {
        dbClient.addUser(secondNotValidatedUser);
        ValidatedUser validatedUser = new ValidatedUser(true);
        User user = new User(secondNotValidatedUser.getId(),
                secondNotValidatedUser.getName(), secondNotValidatedUser.getEmail(), validatedUser.isValidated());
        assertThat(validatorClient.validateUserById(secondNotValidatedUser.getId(), validatedUser)).isEqualTo(user);
    }*/
}
