package com.example.casestudy.services;

import com.example.casestudy.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    List<UserDto> getAllUsers();

    UserDto getUserById(Integer userId);
}
