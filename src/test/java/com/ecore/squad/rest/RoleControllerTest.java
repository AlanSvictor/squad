package com.ecore.squad.rest;

import com.ecore.squad.model.role.Role;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest extends AbstractControllerTest {

    @Test
    public void shouldReturnAllRoles() throws Exception {

        List<Role> expected = getInitialRoles();

        when(roleService.findAll()).thenReturn(expected);
        mockMvc.perform(get("/v1/roles").accept(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(expected.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(expected.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(expected.get(2).getName())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnRoleByName() throws Exception {

        Role tester = getInitialRoles()
                .stream()
                .filter(role -> "Tester".equals(role.getName()))
                .findFirst()
                .orElseThrow();

        when(roleService.findRoleByName(tester.getName())).thenReturn(Optional.of(tester));
        mockMvc.perform(get("/v1/roles/Tester").accept(APPLICATION_JSON))
                .andExpect(jsonPath("name", is(tester.getName())))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundInRoleByName() throws Exception {

        Role tester = getInitialRoles()
                .stream()
                .filter(role -> "Tester".equals(role.getName()))
                .findFirst()
                .orElseThrow();

        when(roleService.findRoleByName(tester.getName())).thenReturn(Optional.of(tester));
        mockMvc.perform(get("/v1/roles/Developer").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldSaveNewRole() throws Exception {

        Role role = new Role("QA");

        String requestBody = "{\"roleName\":\"QA\"}";
        when(roleService.saveNewRole(any())).thenReturn(role);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/roles")
                .accept(MediaType.APPLICATION_JSON).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals("http://localhost/v1/roles/QA",
                result.getResponse().getHeader(HttpHeaders.LOCATION));
    }

    private static List<Role> getInitialRoles() {
        return List.of(new Role("Developer"), new Role("Product Owner"), new Role("Tester"));
    }

}
