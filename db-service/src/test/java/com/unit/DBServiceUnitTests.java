package com.unit;

import com.db.DBConnectionApplication;
import com.db.entity.User;
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
public class DBServiceUnitTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllUsersTest() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/api/all",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        List<User> users = responseEntity.getBody() ;

    }


}
