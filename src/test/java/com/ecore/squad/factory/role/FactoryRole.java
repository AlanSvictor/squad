package com.ecore.squad.factory.role;

import com.ecore.squad.model.role.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FactoryRole {

    public static Role createMemberRole(String roleName) {
        return Role.builder()
                .name(roleName)
                .build();
    }
}
