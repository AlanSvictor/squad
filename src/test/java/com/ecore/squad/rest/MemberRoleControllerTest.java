package com.ecore.squad.rest;

import com.ecore.squad.factory.memberRole.FactoryMemberRole;
import com.ecore.squad.model.memberRole.MemberRole;
import com.ecore.squad.model.memberRole.MemberRoleInputDto;
import com.ecore.squad.model.role.Role;
import com.ecore.squad.model.team.TeamOutputDto;
import com.ecore.squad.model.user.UserOutputDto;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberRoleControllerTest extends AbstractControllerTest {

    @Test
    public void shouldSaveNewMemberRole() throws Exception {
        MemberRole memberRole = FactoryMemberRole.createMemberRole();

        String requestBody = "{\"teamId\":\"7676a4bf-adfe-415c-941b-1739af07039b\",\"userId\":\"197c2b23-1218-44d0-b6b8-d757ba004515\",\"roleName\":\"Tester\"}";
        when(memberRoleService.saveMemberRole(any())).thenReturn(memberRole);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/member-roles/save")
                .accept(MediaType.APPLICATION_JSON).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals("/v1/member-roles?teamId=7676a4bf-adfe-415c-941b-1739af07039c?userId=197c2b23-1218-44d0-b6b8-d757ba004515",
                result.getResponse().getHeader(HttpHeaders.LOCATION));

    }

    @Test
    public void shouldReturnMemberRoleByTeamIdAndUserId() throws Exception {
        MemberRole memberRole = FactoryMemberRole.createMemberRole();

        when(memberRoleService.findRoleByTeamIdAndUserId(memberRole.getTeamId(), memberRole.getUserId())).thenReturn(Optional.of(memberRole));

        mockMvc.perform(get("/v1/member-roles?teamId=7676a4bf-adfe-415c-941b-1739af07039c&userId=197c2b23-1218-44d0-b6b8-d757ba004515")
                        .accept(APPLICATION_JSON))
                .andExpect(jsonPath("teamId", is(memberRole.getTeamId())))
                .andExpect(jsonPath("userId", is(memberRole.getUserId())))
                .andExpect(status().isOk());
    }

    @Test
    public void expectedNotFoundMemberRoleByTeamIdAndUserId() throws Exception {
        MemberRole memberRole = FactoryMemberRole.createMemberRole();

        when(memberRoleService.findRoleByTeamIdAndUserId(memberRole.getTeamId(), memberRole.getUserId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/member-roles?teamId=7676a4bf-adfe-415c-941b-1739af07039c&userId=197c2b23-1218-44d0-b6b8-d757ba004515")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnMemberRoleByTeamId() throws Exception {
        TeamOutputDto teamOutputDto = FactoryMemberRole.createTeamOutputDto();

        when(memberRoleService.findAllMemberRoleByTeamId(teamOutputDto.getTeamId())).thenReturn(Optional.of(teamOutputDto));

        mockMvc.perform(get("/v1/member-roles/team-id/" + teamOutputDto.getTeamId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("teamId", is(teamOutputDto.getTeamId())))
                .andExpect(jsonPath("teamMembers[0].userId", is(teamOutputDto.getTeamMembers().get(0).getUserId())))
                .andExpect(jsonPath("teamMembers[0].roleName", is(teamOutputDto.getTeamMembers().get(0).getRoleName())));

    }

    @Test
    public void expectedNotFoundMemberRoleByTeamId() throws Exception {
        TeamOutputDto teamOutputDto = FactoryMemberRole.createTeamOutputDto();

        when(memberRoleService.findAllMemberRoleByTeamId(teamOutputDto.getTeamId())).thenReturn(Optional.of(teamOutputDto));

        mockMvc.perform(get("/v1/member-roles/team-id/" + UUID.randomUUID())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldReturnAllRoleByUserId() throws Exception {
        UserOutputDto userOutputDto = FactoryMemberRole.createUserOutputDto();

        when(memberRoleService.findAllRolesByUserId(userOutputDto.getUserId())).thenReturn(Optional.of(userOutputDto));

        mockMvc.perform(get("/v1/member-roles/user-id/" + userOutputDto.getUserId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId", is(userOutputDto.getUserId())))
                .andExpect(jsonPath("rolesAttributedInTeams[0].teamId", is(userOutputDto.getRolesAttributedInTeams().get(0).getTeamId())))
                .andExpect(jsonPath("rolesAttributedInTeams[0].roleName", is(userOutputDto.getRolesAttributedInTeams().get(0).getRoleName())))
                .andExpect(jsonPath("rolesAttributedInTeams[1].teamId", is(userOutputDto.getRolesAttributedInTeams().get(1).getTeamId())))
                .andExpect(jsonPath("rolesAttributedInTeams[1].roleName", is(userOutputDto.getRolesAttributedInTeams().get(1).getRoleName())));


    }

    @Test
    public void expectedNotFoundAllRoleByUserId() throws Exception {
        UserOutputDto userOutputDto = FactoryMemberRole.createUserOutputDto();

        when(memberRoleService.findAllRolesByUserId(userOutputDto.getUserId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/member-roles/user-id/" + userOutputDto.getUserId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
