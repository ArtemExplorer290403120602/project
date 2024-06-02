package com.example.project.repository;

import com.example.project.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM requests WHERE user_security_id = :userSecurityId")
    List<Request> findByUser_security_id(Long userSecurityId);
}
