package com.example.project.security.repository;

import com.example.project.model.Response;
import com.example.project.security.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity,Long> {
    Optional<UserSecurity> findByLogin(String login);

    @Query(nativeQuery = true,value = "SELECT * FROM usr_security;")
    List<UserSecurity> allUser();

}
