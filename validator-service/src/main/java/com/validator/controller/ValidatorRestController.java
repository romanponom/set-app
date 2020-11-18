package com.validator.controller;

import com.validator.client.DBClient;
import com.validator.entity.User;
import com.validator.entity.ValidatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ValidatorRestController {

    @Autowired
    private DBClient dbClient;

    @PatchMapping(value = "/validate/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User validateUserById(@PathVariable("id") Long id, @RequestBody ValidatedUser validated) {
        return dbClient.validateUser(id, validated);
    }
}
