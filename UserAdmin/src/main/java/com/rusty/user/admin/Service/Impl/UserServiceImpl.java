package com.rusty.user.admin.Service.Impl;

import com.rusty.user.admin.Entity.User;
import com.rusty.user.admin.Repository.UserRepository;
import com.rusty.user.admin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User partialUpdateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (updatedUser.getName() != null) {user.setName(updatedUser.getName());}
        if (updatedUser.getEmail() != null) {user.setEmail(updatedUser.getEmail());}
        if (updatedUser.getAddress() != null) {user.setAddress(updatedUser.getAddress());}
        if (updatedUser.getPhone() != null) {user.setPhone(updatedUser.getPhone());}
        return userRepository.save(user);
    }

}
