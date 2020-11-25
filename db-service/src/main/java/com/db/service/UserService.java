package com.db.service;

import com.db.entity.User;
import com.db.exception.UserNotFoundException;
import com.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User addOrUpdateUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(getUser(user.getId()));
    }

    @Override
    public List<User> deleteAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        userRepository.deleteAll();
        return users;
    }
}
