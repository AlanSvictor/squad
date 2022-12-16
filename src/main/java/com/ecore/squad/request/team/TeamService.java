package com.ecore.squad.request.team;

import com.ecore.squad.model.team.Team;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

@FeignClient(name = "team", url = "https://cgjresszgg.execute-api.eu-west-1.amazonaws.com")
public interface TeamService {

    @GetMapping(path = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    Collection<Team> findAllTeams();

    @GetMapping(path = "/teams/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Team> findTeamById(@PathVariable(name = "teamId") String teamId);
}
