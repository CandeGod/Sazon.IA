
package com.proyecto.SazonIA.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.SazonIA.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
