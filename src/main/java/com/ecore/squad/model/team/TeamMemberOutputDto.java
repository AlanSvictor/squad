package com.ecore.squad.model.team;

import com.ecore.squad.model.memberRole.MemberRole;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberOutputDto {

    private String userId;

    private String roleName;

    public TeamMemberOutputDto(MemberRole memberRole) {
        this.userId = memberRole.getUserId();
        this.roleName = memberRole.getRole().getName();
    }
}
