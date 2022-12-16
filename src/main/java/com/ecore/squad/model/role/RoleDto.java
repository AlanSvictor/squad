package com.ecore.squad.model.role;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    @NotBlank(message = "Role name is required!")
    private String roleName;
}
