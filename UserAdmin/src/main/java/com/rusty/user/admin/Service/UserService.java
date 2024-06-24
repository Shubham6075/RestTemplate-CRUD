package com.rusty.user.admin.Service;

import com.rusty.user.admin.Entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User createUser(User user);

    List<User> getUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);

    User partialUpdateUser(Long id, User updatedUser);
}
