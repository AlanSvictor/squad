package com.ecore.squad.model.user;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDto {

    private String userId;

    private List<UserRolesOutputDto> rolesAttributedInTeams;
}
