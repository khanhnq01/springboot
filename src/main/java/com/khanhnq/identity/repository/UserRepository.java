package com.khanhnq.identity.repository;

import com.khanhnq.identity.entity.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserTable, String> {
    boolean existsByUsername(String username);
}
