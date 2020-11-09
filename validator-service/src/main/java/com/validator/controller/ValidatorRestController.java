package com.validator.controller;

import com.validator.client.DBClient;
import com.validator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ValidatorRestController {

    @Autowired
    private DBClient dbClient;

    @PatchMapping(value = "/validate/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User validateUserById(@RequestBody User user) {
        return dbClient.validateUser(user);
    }
}
