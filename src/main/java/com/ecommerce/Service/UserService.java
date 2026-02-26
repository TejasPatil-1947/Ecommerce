package com.ecommerce.Service;

import com.ecommerce.Entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User updateUser(Long userId,User user);

    User getUserById(Long userId);

    User getUserByEmail(String email);

    void deleteUser(Long userId);
}
