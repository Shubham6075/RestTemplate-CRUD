package com.rusty.user.management.service;

import com.rusty.user.management.domain.User;

import java.util.List;

public interface ManagementService {
    User createUser(User user);

    List<User> getUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);

    User partialUpdateUser(Long id, User updatedUser);
}
