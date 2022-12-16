package com.ecore.squad.service.memberRole;

import com.ecore.squad.model.memberRole.MemberRole;
import com.ecore.squad.model.memberRole.MemberRoleInputDto;
import com.ecore.squad.model.team.TeamOutputDto;
import com.ecore.squad.model.user.UserOutputDto;

import java.util.Optional;

public interface MemberRoleService {

    MemberRole saveMemberRole(MemberRoleInputDto memberRoleInputDto);

    Optional<MemberRole> findRoleByTeamIdAndUserId(String teamId, String userId);

    Optional<TeamOutputDto> findAllMemberRoleByTeamId(String teamId);

    Optional<UserOutputDto> findAllRolesByUserId(String userId);
}
