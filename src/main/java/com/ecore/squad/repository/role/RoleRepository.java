package com.ecore.squad.repository.role;

import com.ecore.squad.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByNameAllIgnoreCase(@NonNull String name);

    Optional<Role> findByNameAllIgnoreCase(@NonNull String name);

}