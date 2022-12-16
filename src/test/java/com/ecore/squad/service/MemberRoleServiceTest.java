package com.ecore.squad.service;

import com.ecore.squad.exceptions.role.RoleNameNotFoundException;
import com.ecore.squad.exceptions.team.TeamDoesntExistException;
import com.ecore.squad.exceptions.user.UserDoesntBelongsToTheTeamException;
import com.ecore.squad.factory.memberRole.FactoryMemberRole;
import com.ecore.squad.factory.team.FactoryTeam;
import com.ecore.squad.model.memberRole.MemberRole;
import com.ecore.squad.model.memberRole.MemberRoleInputDto;
import com.ecore.squad.model.team.Team;
import com.ecore.squad.model.team.TeamMemberOutputDto;
import com.ecore.squad.model.team.TeamOutputDto;
import com.ecore.squad.model.user.UserOutputDto;
import com.ecore.squad.model.user.UserRolesOutputDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRoleServiceTest extends AbstractServiceTest {


    @Test
    public void shouldSaveNewRoleMember() {
        MemberRoleInputDto productOwner = FactoryMemberRole.createMemberRoleInputDto();

        Team team = FactoryTeam.createTeam();

        when(teamService.findTeamById(team.getId())).thenReturn(Optional.of(team));

        MemberRole memberRole = memberRoleService.saveMemberRole(productOwner);
        Assert.assertNotNull(memberRole);
        Assert.assertEquals(productOwner.getTeamId(), memberRole.getTeamId());
        Assert.assertEquals(productOwner.getUserId(), memberRole.getUserId());
        Assert.assertEquals(productOwner.getRoleName(), memberRole.getRole().getName());
    }

    @Test
    public void shouldReturnTeamDoesntExistException() {
        MemberRoleInputDto productOwner = FactoryMemberRole.createMemberRoleInputDto();
        when(teamService.findTeamById(any())).thenReturn(Optional.empty());
        assertThrows(TeamDoesntExistException.class, () -> memberRoleService.saveMemberRole(productOwner), TeamDoesntExistException.MESSAGE);
    }

    @Test
    public void shouldReturnUserDoesntBelongsToTheTeamException() {
        MemberRoleInputDto productOwner = FactoryMemberRole.createMemberRoleInputDto();

        Team team = FactoryTeam.createTeam();
        Set<String> teamMembers = team.getTeamMemberIds().stream().filter(s -> !s.equals(productOwner.getUserId())).collect(Collectors.toSet());
        team.setTeamMemberIds(teamMembers);

        when(teamService.findTeamById(any())).thenReturn(Optional.of(team));
        assertThrows(UserDoesntBelongsToTheTeamException.class, () -> memberRoleService.saveMemberRole(productOwner), UserDoesntBelongsToTheTeamException.MESSAGE);
    }

    @Test
    public void shouldReturnRoleNotFoundException() {
        MemberRoleInputDto memberRoleInputDto = FactoryMemberRole.createMemberRoleInputDto();
        memberRoleInputDto.setRoleName("Product designer");

        Team team = FactoryTeam.createTeam();

        when(teamService.findTeamById(any())).thenReturn(Optional.of(team));
        assertThrows(RoleNameNotFoundException.class, () -> memberRoleService.saveMemberRole(memberRoleInputDto), RoleNameNotFoundException.MESSAGE);
    }

    @Test
    public void shouldReturnAllMemberRoleByTeamId() {
        Team team = FactoryTeam.createTeam();
        String memberIdExpected = "197c2b23-1218-44d0-b6b8-d757ba004515";
        when(teamService.findTeamById(team.getId())).thenReturn(Optional.of(team));

        Optional<TeamOutputDto> roleByTeamId = memberRoleService.findAllMemberRoleByTeamId(team.getId());
        Assert.assertTrue(roleByTeamId.isPresent());
        Assert.assertEquals(roleByTeamId.get().getTeamId(), team.getId());

        TeamMemberOutputDto result =
                roleByTeamId.get().getTeamMembers()
                        .stream()
                        .filter(teamMemberOutputDto -> teamMemberOutputDto.getUserId().equals(memberIdExpected))
                        .findFirst()
                        .orElseThrow();

        Assert.assertEquals(memberIdExpected, result.getUserId());
        Assert.assertEquals("Developer", result.getRoleName());
    }

    @Test
    public void shouldThrownTeamDoesntExistException() {
        Team team = FactoryTeam.createTeam();
        String teamId = UUID.randomUUID().toString();

        when(teamService.findTeamById(team.getId())).thenReturn(Optional.of(team));
        assertThrows(TeamDoesntExistException.class, () -> memberRoleService.findAllMemberRoleByTeamId(teamId), TeamDoesntExistException.MESSAGE);
    }

    @Test
    public void shouldReturnAllRolesByUserId() {

        String userId = "91357746-c829-42ee-b783-87b44e8ca4a7";
        String teamId1 = "89a50743-f60b-4345-a772-9d3c68021408";
        String teamId2 = "b1bd995c-9219-4eea-a7c4-f5c154a7e551";
        String productOwner = "Product Owner";
        String tester = "Tester";

        Team team1 = FactoryTeam.createTeam(teamId1, List.of(userId));
        Team team2 = FactoryTeam.createTeam(teamId2, List.of(userId));

        when(teamService.findAllTeams()).thenReturn(List.of(team1, team2));
        when(teamService.findTeamById(team1.getId())).thenReturn(Optional.of(team1));
        when(teamService.findTeamById(team2.getId())).thenReturn(Optional.of(team2));


        MemberRoleInputDto inputTester =
                MemberRoleInputDto.builder()
                        .teamId(teamId1)
                        .userId(userId)
                        .roleName(tester)
                        .build();
        MemberRole memberRoleTester = memberRoleService.saveMemberRole(inputTester);
        Assert.assertNotNull(memberRoleTester);
        Assert.assertEquals(tester, memberRoleTester.getRole().getName());


        MemberRoleInputDto inputProductOwner =
                MemberRoleInputDto.builder()
                        .teamId(teamId2)
                        .userId(userId)
                        .roleName(productOwner)
                        .build();

        MemberRole memberRoleProductOwner = memberRoleService.saveMemberRole(inputProductOwner);
        Assert.assertNotNull(memberRoleProductOwner);
        Assert.assertEquals(productOwner, memberRoleProductOwner.getRole().getName());

        Optional<UserOutputDto> allRolesByUserId = memberRoleService.findAllRolesByUserId(userId);
        Assert.assertTrue(allRolesByUserId.isPresent());
        Assert.assertEquals(2, allRolesByUserId.get().getRolesAttributedInTeams().size());

    }

}
