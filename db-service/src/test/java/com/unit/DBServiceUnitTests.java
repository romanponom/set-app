package com.unit;

import com.db.DBConnectionApplication;
import com.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DBConnectionApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class DBServiceUnitTests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;

    @Test
    @DisplayName("Get all users")
    public void getAllUsersTest() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/api/all",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        List<User> users = responseEntity.getBody();
        User expectedDefaultUser = new User(1, "admin", "admin@aa.ww", true);
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).isEqualTo(expectedDefaultUser);
    }


}
