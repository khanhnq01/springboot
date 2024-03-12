package com.khanhnq.identity.service;

import com.khanhnq.identity.dto.request.UserCreationRequest;
import com.khanhnq.identity.dto.request.UserUpdateRequest;
import com.khanhnq.identity.entity.UserTable;
import com.khanhnq.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public UserTable createUser(UserCreationRequest request){
        UserTable userNew = new UserTable();

        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("User existed.");
        }

        userNew.setUsername(request.getUsername());
        userNew.setPassword(request.getPassword());
        userNew.setFirstName(request.getFirstName());
        userNew.setLastName(request.getLastName());
        userNew.setDob(request.getDob());

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

        userTable.setPassword(request.getPassword());
        userTable.setFirstName(request.getFirstName());
        userTable.setLastName(request.getLastName());
        userTable.setDob(request.getDob());

        return userRepository.save(userTable);
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
