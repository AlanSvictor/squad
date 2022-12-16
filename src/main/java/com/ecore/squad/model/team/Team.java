package com.ecore.squad.model.team;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Data
@Builder
public class Team {

    private String id;

    private String name;

    private String teamLead;

    private Collection<String> teamMemberIds;

}
