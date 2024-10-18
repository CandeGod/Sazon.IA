package com.proyecto.SazonIA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.SazonIA.model.OpenAIRequest;

import jakarta.transaction.Transactional;

public interface OpenAIRequestRepository extends JpaRepository<OpenAIRequest,Integer>{
    @Modifying
    @Transactional
    @Query("DELETE FROM OpenAIRequest o WHERE o.user.userId = ?1")
    void deleteByUserId(Integer userId);
    
    @Query("SELECT o FROM OpenAIRequest o WHERE o.user.userId = ?1")
    List<OpenAIRequest> findByUserId(Integer userId);
}
