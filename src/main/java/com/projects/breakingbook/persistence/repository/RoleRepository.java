package com.projects.breakingbook.persistence.repository;

import com.projects.breakingbook.persistence.entity.Role;
import com.projects.breakingbook.persistence.entity.RoleName;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(RoleName roleName);
}