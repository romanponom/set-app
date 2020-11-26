package com.unit;

import com.db.DBConnectionApplication;
import com.db.entity.User;
import com.db.exception.UserNotFoundException;
import com.db.repository.UserRepository;
import com.db.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = DBConnectionApplication.class)
@ExtendWith(MockitoExtension.class)
public class DbServiceUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(DbServiceUnitTest.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService service;

    private List<User> users;
    private User user;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        user = new User(2, "qqqq", "eeee@ww.ee", false);
        users.add(new User(1, "admin", "admin@ww.ee", true));
        users.add(user);
    }

    @Test
    @DisplayName("[UNIT-TEST] Get all users")
    public void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(users);
        assertThat(service.findAll()).isEqualTo(users);
    }

    @Test
    @DisplayName("[UNIT-TEST] Add user")
    public void addUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        User addedUser = service.addOrUpdateUser(user);
        assertThat(addedUser).isEqualTo(user);
    }

    @Test
    @DisplayName("[UNIT-TEST] Get user")
    public void getUserTest() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        User gotUser = service.getUser(2L);
        assertThat(gotUser).isEqualTo(user);
    }

    @Test
    @DisplayName("[UNIT-TEST] Delete user")
    public void deleteUserTest() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        service.deleteUser(user);
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("[UNIT-TEST] Delete not existed user")
    public void deleteNotExistedUserTest() {
        assertThrows(UserNotFoundException.class, () -> service.deleteUser(user));
    }

    @Test
    @DisplayName("[UNIT-TEST] Delete all users")
    public void deleteAllUsersTest() {
        when(userRepository.findAll()).thenReturn(users);
        service.deleteAllUsers();
        verify(userRepository).deleteAll();
    }
}
