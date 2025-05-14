package com.example.Library.service;

import com.example.Library.dto.UserCreateRequest;
import com.example.Library.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserCreateRequest request);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserCreateRequest request);
    void deleteUser(Long id);
    List<UserDTO> createUserList(List<UserCreateRequest> requestList);
}
