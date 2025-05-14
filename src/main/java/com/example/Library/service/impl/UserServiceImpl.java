package com.example.Library.service.impl;

import com.example.Library.dto.UserCreateRequest;
import com.example.Library.dto.UserDTO;
import com.example.Library.model.User;
import com.example.Library.repository.UserRepository;
import com.example.Library.service.UserService;
import com.example.Library.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDTO updateUser(Long id, UserCreateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> createUserList(List<UserCreateRequest> requestList) {
        List<User> users = requestList.stream().map(req -> {
            if (userRepository.existsByEmail(req.getEmail())) {
                throw new IllegalArgumentException("Email already in use: " + req.getEmail());
            }

            return User.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .role(req.getRole())
                    .build();
        }).toList();

        List<User> savedUsers = userRepository.saveAll(users);
        return savedUsers.stream().map(userMapper::toDto).toList();
    }
}