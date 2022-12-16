package com.ecore.squad.service;

import com.ecore.squad.exceptions.role.RoleAlreadyExistException;
import com.ecore.squad.exceptions.role.RoleNameInvalidException;
import com.ecore.squad.factory.role.FactoryRole;
import com.ecore.squad.model.role.Role;
import com.ecore.squad.model.role.RoleDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest extends AbstractServiceTest {

    @Test
    public void shouldReturnCreatedRole() {
        Role role = FactoryRole.createMemberRole("QA");
        roleRepository.save(role);

        Role roleStored = roleService.findRoleByName(role.getName()).orElseThrow();
        Assert.assertEquals(role.getName(), roleStored.getName());
    }

    @Test
    public void shouldSaveNewRoleWithSuccess() {
        RoleDto qa = new RoleDto("QA");
        Role role = roleService.saveNewRole(qa);
        Assert.assertNotNull(role);
    }

    @Test
    public void shouldReturnNullForNotExistingRole() {
        Optional<Role> scrumMaster = roleService.findRoleByName("Scrum master");
        Assert.assertTrue(scrumMaster.isEmpty());
    }

    @Test
    public void shouldReturnRoleAlreadyExistException(){
        RoleDto developer = new RoleDto("Developer");
        assertThrows(RoleAlreadyExistException.class, () -> roleService.saveNewRole(developer), RoleAlreadyExistException.MESSAGE);
    }

    @Test
    public void shouldReturnRoleNameInvalidException(){
        RoleDto developer = new RoleDto("Scrum   ");
        assertThrows(RoleNameInvalidException.class, () -> roleService.saveNewRole(developer), RoleNameInvalidException.MESSAGE);
    }

    @Test
    public void shouldReturnAllRoles() {
        Collection<Role> all = roleService.findAll();
        assertEquals(3, all.size());
    }

}
