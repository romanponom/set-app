package com.service;

import com.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User getUser(Long id);
    User addOrUpdateUser(User user);
    void deleteUser(User user);
    User findById(Long id);
    List<User> deleteAllUsers();
}
