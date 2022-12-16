package com.ecore.squad.controller.memberRole;

import com.ecore.squad.model.memberRole.MemberRole;
import com.ecore.squad.model.memberRole.MemberRoleInputDto;
import com.ecore.squad.model.memberRole.MemberRoleOutputDto;
import com.ecore.squad.model.team.TeamOutputDto;
import com.ecore.squad.model.user.UserOutputDto;
import com.ecore.squad.service.memberRole.MemberRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/member-roles")
public class MemberRoleController {

    private final MemberRoleService memberRoleService;

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewMemberRole(@Valid @RequestBody MemberRoleInputDto memberRoleInputDto) {
        MemberRole memberRole = memberRoleService.saveMemberRole(memberRoleInputDto);
        URI location = URI.create("/v1/member-roles?teamId=" + memberRole.getTeamId() + "?userId=" + memberRole.getUserId());
        return ResponseEntity
                .created(location).body(memberRole);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findRoleByTeamIdAndUserId(@RequestParam(name = "teamId") String teamId,
                                                       @RequestParam(name = "userId") String userId) {
        Optional<MemberRole> memberRole = memberRoleService.findRoleByTeamIdAndUserId(teamId, userId);
        return memberRole.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(new MemberRoleOutputDto(memberRole.get())) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/team-id/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllMemberRoleByTeamId(@PathVariable(name = "teamId") String teamId) {
        Optional<TeamOutputDto> teamOutput = memberRoleService.findAllMemberRoleByTeamId(teamId);
        return teamOutput.isEmpty() ?
                ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(teamOutput.get());
    }

    @GetMapping(path = "/user-id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllRolesByUserId(@PathVariable(name = "userId") String userId) {
        Optional<UserOutputDto> userOutputDto = memberRoleService.findAllRolesByUserId(userId);
        return userOutputDto.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(userOutputDto.get());
    }


}
