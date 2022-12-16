package com.ecore.squad.service.role;

import com.ecore.squad.model.role.Role;
import com.ecore.squad.model.role.RoleDto;

import java.util.Collection;
import java.util.Optional;

public interface RoleService {
    Collection<Role> findAll();

    Role saveNewRole(RoleDto roleDto);

    Optional<Role> findRoleByName(String roleName);

}
