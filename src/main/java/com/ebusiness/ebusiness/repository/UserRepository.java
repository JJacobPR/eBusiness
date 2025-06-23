package com.ebusiness.ebusiness.repository;

import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getUserEntityByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    @Query(value = "SELECT r.name FROM users u JOIN user_roles ur ON u.user_id = ur.user_id JOIN role r ON r.role_id = ur.role_id WHERE u.email = :email", nativeQuery = true)
    List<String> findRolesByEmail(@Param("email") String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
