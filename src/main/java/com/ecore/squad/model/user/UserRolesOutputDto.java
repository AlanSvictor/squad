package com.ecore.squad.model.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesOutputDto {

    private String teamId;
    private String roleName;
}
