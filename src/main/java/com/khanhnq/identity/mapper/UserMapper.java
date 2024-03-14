package com.khanhnq.identity.mapper;

import com.khanhnq.identity.dto.request.UserCreationRequest;
import com.khanhnq.identity.dto.request.UserUpdateRequest;
import com.khanhnq.identity.dto.response.UserResponse;
import com.khanhnq.identity.entity.UserTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserTable toUser(UserCreationRequest userCreationRequest);
    void updateUser(@MappingTarget UserTable userTable, UserUpdateRequest userUpdateRequest);
    UserResponse toUserResponse(UserTable userTable);

}
