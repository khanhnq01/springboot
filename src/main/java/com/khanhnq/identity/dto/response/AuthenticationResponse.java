package com.khanhnq.identity.dto.response;

import com.khanhnq.identity.repository.UserRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    boolean authenticated;
    UserRepository userRepository;
}