package com.ecore.squad.rest;

import com.ecore.squad.service.memberRole.MemberRoleService;
import com.ecore.squad.service.role.RoleService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected RoleService roleService;

    @MockBean
    protected MemberRoleService memberRoleService;

    @Before
    public void setUp() {
        Mockito.reset(roleService, memberRoleService);
    }
}
