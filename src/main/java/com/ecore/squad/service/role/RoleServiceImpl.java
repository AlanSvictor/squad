package com.ecore.squad.service.role;

import com.ecore.squad.exceptions.role.RoleAlreadyExistException;
import com.ecore.squad.exceptions.role.RoleNameInvalidException;
import com.ecore.squad.model.role.Role;
import com.ecore.squad.model.role.RoleDto;
import com.ecore.squad.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Collection<Role> findAll() {
        log.info("RoleServiceImpl findAll");
        return roleRepository.findAll();
    }

    @Override
    public Role saveNewRole(RoleDto roleDto) {
        log.info("saveNewRole");
        validateRoleDto(roleDto);
        return roleRepository.save(new Role(roleDto.getRoleName()));
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        log.info("findRoleByName");
        return roleRepository.findByNameAllIgnoreCase(roleName);
    }

    private void validateRoleDto(RoleDto roleDto) {
        if (!isValidRoleName(roleDto.getRoleName())) {
            throw new RoleNameInvalidException();
        }

        if (existsRoleByName(roleDto.getRoleName())) {
            throw new RoleAlreadyExistException();
        }
    }

    private boolean existsRoleByName(String roleName) {
        return roleRepository.existsByNameAllIgnoreCase(roleName);
    }

    private static boolean isValidRoleName(String name) {

        String regex = "[a-zA-Z]+(\\s)?+[a-zA-Z]+";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(name);

        return m.matches();
    }
}
