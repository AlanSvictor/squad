package com.ecore.squad.model.team;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamOutputDto {

    private String teamId;

    private List<TeamMemberOutputDto> teamMembers;
}
