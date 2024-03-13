package com.khanhnq.identity.service;

import com.khanhnq.identity.dto.request.UserCreationRequest;
import com.khanhnq.identity.dto.request.UserUpdateRequest;
import com.khanhnq.identity.entity.UserTable;
import com.khanhnq.identity.exception.AppException;
import com.khanhnq.identity.exception.ErrorCode;
import com.khanhnq.identity.mapper.UserMapper;
import com.khanhnq.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;


    public UserTable createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserTable userNew = userMapper.toUser(request);
        return userRepository.save(userNew);
    }
    public List<UserTable> getUserList(){
        return userRepository.findAll();
    }
    public UserTable getUserById(String id){
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));
    }
    public UserTable updateUser(String userId ,UserUpdateRequest request){
        UserTable userTable = getUserById(userId);

        userTable.builder()
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        return userRepository.save(userTable);
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
