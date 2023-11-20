package com.exerciseapp.myapp.repository;

import com.exerciseapp.myapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRoleName(String roleName);
}
