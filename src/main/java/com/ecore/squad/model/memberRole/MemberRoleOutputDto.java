package com.ecore.squad.model.memberRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleOutputDto {

    private String teamId;

    private String userId;

    private String roleName;

    public MemberRoleOutputDto(MemberRole memberRole) {
        this.teamId = memberRole.getTeamId();
        this.userId = memberRole.getUserId();
        this.roleName = memberRole.getRole().getName();
    }
}
