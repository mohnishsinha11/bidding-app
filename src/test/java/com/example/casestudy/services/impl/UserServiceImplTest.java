package com.example.casestudy.services.impl;

import com.example.casestudy.entities.User;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.payloads.UserDto;
import com.example.casestudy.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserRepo userRepo;

    @Test
    public void given_User_Details_when_Create_User_Method_is_Invoked_then_Save_User() {
        UserDto inputUserDto = new UserDto();
        inputUserDto.setName("Harry Kane");
        inputUserDto.setEmail("harry@example.com");
        inputUserDto.setPassword("password1");

        User userEntity = new User();
        userEntity.setName("Harry Kane");
        userEntity.setEmail("harry@example.com");
        userEntity.setPassword("password1");

        User savedUserEntity = new User();
        savedUserEntity.setName("Harry Kane");
        savedUserEntity.setEmail("harry@example.com");

        when(modelMapper.map(inputUserDto, User.class)).thenReturn(userEntity);

        when(userRepo.save(userEntity)).thenReturn(savedUserEntity);

        when(modelMapper.map(savedUserEntity, UserDto.class)).thenReturn(inputUserDto);
        UserDto userResultDto = userServiceImpl.createUser(inputUserDto);

        assertEquals(inputUserDto.getName(), userResultDto.getName());
        assertEquals(inputUserDto.getEmail(), userResultDto.getEmail());

        verify(userRepo, times(1)).save(userEntity);
    }

    @Test
    public void test_Get_All_Users() {
        User user1 = new User();
        user1.setName("Harry");
        user1.setEmail("harry@example.com");

        User user2 = new User();
        user2.setName("Tim");
        user2.setEmail("tim@example.com");

        List<User> userList = Arrays.asList(user1, user2);

        UserDto userDto1 = new UserDto();
        userDto1.setName("Harry");
        userDto1.setEmail("harry@example.com");

        UserDto userDto2 = new UserDto();
        userDto2.setName("Tim");
        userDto2.setEmail("Tim@example.com");

        List<UserDto> expectedUserDtos = Arrays.asList(userDto1, userDto2);

        when(userRepo.findAll()).thenReturn(userList);

        when(modelMapper.map(user1, UserDto.class)).thenReturn(userDto1);
        when(modelMapper.map(user2, UserDto.class)).thenReturn(userDto2);

        List<UserDto> resultUserDtos = userServiceImpl.getAllUsers();

        assertEquals(expectedUserDtos, resultUserDtos);
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void test_Get_User_By_Id_Success() {
        User user = new User();
        user.setName("Harry");
        user.setEmail("harry@example.com");

        UserDto userDto = new UserDto();
        userDto.setName("Harry");
        userDto.setEmail("harry@example.com");

        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto resultUserDto = userServiceImpl.getUserById(1);

        assertEquals(userDto, resultUserDto);
        verify(userRepo, times(1)).findById(anyInt());
    }

    @Test
    public void test_Get_User_By_Id_when_userId_does_not_exists_then_throw_exception() {

        Integer invalidUserId = 21;
        when(userRepo.findById(invalidUserId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()-> {userServiceImpl.getUserById(21);});
        verify(userRepo, times(1)).findById(anyInt());
    }
}
