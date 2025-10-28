package com.winwintravel.authapi.repository;

import com.winwintravel.authapi.dto.UserDTO;
import com.winwintravel.authapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u from User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u.id from User u where u.email = :email")
    UUID findUuidByEmail(@Param("email") String email);
}
