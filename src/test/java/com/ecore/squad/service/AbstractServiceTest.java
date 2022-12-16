package com.ecore.squad.service;

import com.ecore.squad.repository.memberRole.MemberRoleRepository;
import com.ecore.squad.repository.role.RoleRepository;
import com.ecore.squad.request.team.TeamService;
import com.ecore.squad.service.memberRole.MemberRoleService;
import com.ecore.squad.service.role.RoleService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractServiceTest {

    @Autowired
    MemberRoleRepository memberRoleRepository;

    @Autowired
    RoleService roleService;

    @MockBean
    TeamService teamService;

    @Autowired
    MemberRoleService memberRoleService;

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void setUp() {
        Mockito.reset(teamService);
    }
}
