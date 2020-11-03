package com.service;

import com.entity.User;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User addOrUpdateUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


}
