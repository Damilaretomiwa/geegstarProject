package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);

    Page<User> findAllByRole(Role role, Pageable pageable);
}
