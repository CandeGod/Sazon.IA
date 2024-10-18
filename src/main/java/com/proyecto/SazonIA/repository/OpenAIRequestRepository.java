package com.proyecto.SazonIA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.SazonIA.model.OpenAIRequest;

public interface OpenAIRequestRepository extends JpaRepository<OpenAIRequest,Integer>{
    
}
