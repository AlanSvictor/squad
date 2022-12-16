package com.ecore.squad.service.memberRole;

import com.ecore.squad.exceptions.role.RoleNameNotFoundException;
import com.ecore.squad.exceptions.team.TeamDoesntExistException;
import com.ecore.squad.exceptions.user.UserDoesntBelongsToTheTeamException;
import com.ecore.squad.model.memberRole.MemberRole;
import com.ecore.squad.model.memberRole.MemberRoleInputDto;
import com.ecore.squad.model.role.Role;
import com.ecore.squad.model.team.Team;
import com.ecore.squad.model.team.TeamMemberOutputDto;
import com.ecore.squad.model.team.TeamOutputDto;
import com.ecore.squad.model.user.UserOutputDto;
import com.ecore.squad.model.user.UserRolesOutputDto;
import com.ecore.squad.repository.memberRole.MemberRoleRepository;
import com.ecore.squad.request.team.TeamService;
import com.ecore.squad.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRoleServiceImpl implements MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    private final TeamService teamService;

    private final RoleService roleService;

    @Override
    public MemberRole saveMemberRole(MemberRoleInputDto memberRoleInputDto) {

        validateTeamAndUser(memberRoleInputDto);

        Optional<Role> role = roleService.findRoleByName(memberRoleInputDto.getRoleName());
        if (role.isEmpty()) {
            throw new RoleNameNotFoundException();
        }

        MemberRole memberRole = findRoleByTeamIdAndUserId(memberRoleInputDto.getTeamId(), memberRoleInputDto.getUserId())
                .orElse(new MemberRole(memberRoleInputDto.getTeamId(), memberRoleInputDto.getUserId()));
        memberRole.setRole(role.get());

        return saveMemberRole(memberRole);
    }

    @Override
    public Optional<MemberRole> findRoleByTeamIdAndUserId(String teamId, String userId) {
        return memberRoleRepository.findByTeamIdAndUserId(teamId, userId);
    }

    @Override
    public Optional<TeamOutputDto> findAllMemberRoleByTeamId(String teamId) {

        Optional<Team> optionalTeam = teamService.findTeamById(teamId);
        if (optionalTeam.isEmpty()) {
            throw new TeamDoesntExistException();
        }

        List<TeamMemberOutputDto> teamMemberOutputDtos = optionalTeam.get().getTeamMemberIds()
                .stream()
                .filter(Objects::nonNull)
                .map(user -> {
                    Optional<MemberRole> optionalMemberRole = findRoleByTeamIdAndUserId(teamId, user);
                    return optionalMemberRole.orElseGet(() -> saveMemberRole(new MemberRole(teamId, user)));
                })
                .map(TeamMemberOutputDto::new)
                .toList();

        return teamMemberOutputDtos.isEmpty() ? Optional.empty() : Optional.of(new TeamOutputDto(teamId, teamMemberOutputDtos));
    }

    @Override
    public Optional<UserOutputDto> findAllRolesByUserId(String userId) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setUserId(userId);
        List<UserRolesOutputDto> userRolesOutputDtos = new ArrayList<>();

        teamService.findAllTeams()
                .stream()
                .filter(Objects::nonNull)
                .map(populatedTeamInfo())
                .filter(team -> verifyIfUserIdBelongsToTeamId(team, userId))
                .forEach(team -> {
                    MemberRole memberRole = findRoleByTeamIdAndUserId(team.getId(), userId).orElseGet(() -> saveMemberRole(new MemberRole(team.getId(), userId)));
                    UserRolesOutputDto userRolesOutputDto = new UserRolesOutputDto(team.getId(), memberRole.getRole().getName());
                    userRolesOutputDtos.add(userRolesOutputDto);
                });

        userOutputDto.setRolesAttributedInTeams(userRolesOutputDtos);

        return userRolesOutputDtos.isEmpty() ? Optional.empty() : Optional.of(userOutputDto);
    }

    private void validateTeamAndUser(MemberRoleInputDto memberRoleInputDto) {
        Optional<Team> optionalTeam = teamService.findTeamById(memberRoleInputDto.getTeamId());
        if (optionalTeam.isEmpty()) {
            throw new TeamDoesntExistException();
        }

        boolean isUserInTeam = verifyIfUserIdBelongsToTeamId(optionalTeam.get(), memberRoleInputDto.getUserId());
        if (!isUserInTeam) {
            throw new UserDoesntBelongsToTheTeamException();
        }
    }

    private boolean verifyIfUserIdBelongsToTeamId(Team team, String userId) {
        return team.getTeamMemberIds().stream().anyMatch(userId::equals);
    }

    private Function<Team, Team> populatedTeamInfo() {
        return team -> {
            teamService.findTeamById(team.getId())
                    .ifPresent(value -> {
                        team.setTeamLead(value.getTeamLead());
                        team.setTeamMemberIds(value.getTeamMemberIds());
                    });
            return team;
        };
    }

    private MemberRole saveMemberRole(MemberRole memberRole) {
        return memberRoleRepository.save(memberRole);
    }

}
