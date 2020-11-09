package com.db.service;


import com.db.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User getUser(Long id);
    User addOrUpdateUser(User user);
    void deleteUser(User user);
    List<User> deleteAllUsers();
}
