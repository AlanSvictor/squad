package com.ecore.squad.factory.team;

import com.ecore.squad.model.team.Team;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class FactoryTeam {

    public static Team createTeam() {
        return Team.builder()
                .id("7676a4bf-adfe-415c-941b-1739af07039b")
                .name("Ordinary Coral Lynx")
                .teamLead("b12fa35a-9c4c-4bf9-8f32-27cf03a1f190")
                .teamMemberIds(List.of("371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
                        "54383a18-425c-4f50-9424-1c4c27e776dd",
                        "e0dba3dc-313d-4648-bd9c-4ddc8b189e84",
                        "b047d3f4-3469-47ce-a03f-1637a6de036b",
                        "ee91a519-fefa-48a7-bdf7-672bde38aef9",
                        "197c2b23-1218-44d0-b6b8-d757ba004515",
                        "e947058e-2d5f-47d9-925b-27bcab14c38e"))
                .build();
    }


    public static Team createTeam(String teamId, List<String> teamMembers) {
        return Team.builder()
                .id(teamId)
                .teamLead(UUID.randomUUID().toString())
                .teamMemberIds(teamMembers)
                .build();
    }
}
