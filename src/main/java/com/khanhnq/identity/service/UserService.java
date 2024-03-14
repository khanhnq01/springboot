package com.khanhnq.identity.service;

import com.khanhnq.identity.dto.request.UserCreationRequest;
import com.khanhnq.identity.dto.request.UserUpdateRequest;
import com.khanhnq.identity.dto.response.UserResponse;
import com.khanhnq.identity.entity.UserTable;
import com.khanhnq.identity.exception.AppException;
import com.khanhnq.identity.exception.ErrorCode;
import com.khanhnq.identity.mapper.UserMapper;
import com.khanhnq.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;


    public UserTable createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserTable userNew = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userNew.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(userNew);
    }

    public List<UserTable> getUserList() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        UserTable userTable = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));
        userMapper.updateUser(userTable, request);

        return userMapper.toUserResponse(userRepository.save(userTable));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
