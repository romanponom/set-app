package unit.test;

import com.admin.AdminApplication;
import com.admin.controller.AdminApplicationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes={AdminApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {

    @Autowired
    private AdminApplicationController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
