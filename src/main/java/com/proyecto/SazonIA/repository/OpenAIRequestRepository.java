package com.proyecto.SazonIA.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyecto.SazonIA.model.OpenAIRequest;

import jakarta.transaction.Transactional;

public interface OpenAIRequestRepository extends JpaRepository<OpenAIRequest,Integer>{
    @Modifying
    @Transactional
    @Query("DELETE FROM OpenAIRequest o WHERE o.user.user_id = ?1")
    void deleteByUserId(Integer userId);
    
    @Query("SELECT o FROM OpenAIRequest o WHERE o.user.user_id = ?1")
    Page<OpenAIRequest> findByUserId(Integer userId, Pageable pageable);
}
