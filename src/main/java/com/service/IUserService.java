package com.service;

import com.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User getUser(Long id);
    Long addOrUpdateUser(User user);
    void deleteUser(User user);
}
