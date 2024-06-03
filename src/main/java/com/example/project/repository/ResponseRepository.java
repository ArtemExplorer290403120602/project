package com.example.project.repository;

import com.example.project.model.Request;
import com.example.project.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM responses;")
    List<Response> allResponses();
}
