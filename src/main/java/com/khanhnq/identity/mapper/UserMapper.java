package com.khanhnq.identity.mapper;

import com.khanhnq.identity.dto.request.UserCreationRequest;
import com.khanhnq.identity.entity.UserTable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserTable toUser(UserCreationRequest userCreationRequest);

}
