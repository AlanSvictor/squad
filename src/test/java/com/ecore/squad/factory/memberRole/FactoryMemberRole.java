package com.ecore.squad.factory.memberRole;

import com.ecore.squad.factory.role.FactoryRole;
import com.ecore.squad.model.memberRole.MemberRole;
import com.ecore.squad.model.memberRole.MemberRoleInputDto;
import com.ecore.squad.model.team.TeamMemberOutputDto;
import com.ecore.squad.model.team.TeamOutputDto;
import com.ecore.squad.model.user.UserOutputDto;
import com.ecore.squad.model.user.UserRolesOutputDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class FactoryMemberRole {

    public static MemberRole createMemberRole() {
        return MemberRole.builder()
                .id("7676a4bf-adfe-415c-941b-1739af07039c")
                .userId("197c2b23-1218-44d0-b6b8-d757ba004515")
                .teamId("7676a4bf-adfe-415c-941b-1739af07039c")
                .role(FactoryRole.createMemberRole("Tester"))
                .build();
    }

    public static TeamOutputDto createTeamOutputDto() {
        return TeamOutputDto.builder()
                .teamId("7676a4bf-adfe-415c-941b-1739af07039c")
                .teamMembers(List.of(TeamMemberOutputDto.builder()
                                .userId("197c2b23-1218-44d0-b6b8-d757ba004515")
                                .roleName("Developer")
                                .build(),
                        TeamMemberOutputDto.builder()
                                .userId("e947058e-2d5f-47d9-925b-27bcab14c38e")
                                .roleName("Tester")
                                .build(),
                        TeamMemberOutputDto.builder()
                                .userId("e947058e-2d5f-47d9-925b-27bcab14c38e")
                                .roleName("Product Owner")
                                .build(),
                        TeamMemberOutputDto.builder()
                                .userId("e947058e-2d5f-47d9-925b-27bcab14c38e")
                                .roleName("QA")
                                .build()))
                .build();

    }

    public static UserOutputDto createUserOutputDto() {

        return UserOutputDto.builder()
                .userId("197c2b23-1218-44d0-b6b8-d757ba004515")
                .rolesAttributedInTeams(List.of(UserRolesOutputDto.builder()
                                .teamId("7676a4bf-adfe-415c-941b-1739af07039c")
                                .roleName("Developer")
                                .build(),
                        UserRolesOutputDto.builder()
                                .teamId("91357746-c829-42ee-b783-87b44e8ca4a7")
                                .roleName("QA")
                                .build()))
                .build();
    }

    public static MemberRoleInputDto createMemberRoleInputDto() {
        return MemberRoleInputDto.builder()
                .teamId("7676a4bf-adfe-415c-941b-1739af07039b")
                .userId("371d2ee8-cdf4-48cf-9ddb-04798b79ad9e")
                .roleName("Product Owner")
                .build();
    }


}
