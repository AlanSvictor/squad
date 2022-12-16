package com.ecore.squad.model.memberRole;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleInputDto {

    @NotBlank(message = "User id is required!")
    private String teamId;

    @NotBlank(message = "User id is required!")
    private String userId;

    @NotBlank(message = "Role name is required!")
    private String roleName;

}
