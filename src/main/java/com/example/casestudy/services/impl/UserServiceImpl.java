package com.example.casestudy.services.impl;

import com.example.casestudy.entities.User;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.payloads.UserDto;
import com.example.casestudy.repositories.UserRepo;
import com.example.casestudy.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> listOfUsers = userRepo.findAll();
        List<UserDto> userDtos = listOfUsers.stream()
                .map(user->modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User", "UserId", userId));
        return modelMapper.map(user, UserDto.class);
    }
}
