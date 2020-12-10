package com.component;

import com.component.config.DbClientFeignConfig;
import com.validator.client.DBClient;
import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = DbClientFeignConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidatorServiceIntegrationTest {

    @Autowired
    private DBClient client;

    @Test
    public void validateUserTest() {
        User user = new User(1L, "admin", "admin@aa.ww", true);
        ValidatedUser validatedUser = new ValidatedUser(true);
        assertThat(client.validateUser((long) 1, validatedUser)).isEqualTo(user);
    }
}
