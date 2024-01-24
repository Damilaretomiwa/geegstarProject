package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByEmail(String userEmail);
}
