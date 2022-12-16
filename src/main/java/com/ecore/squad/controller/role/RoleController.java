package com.ecore.squad.controller.role;

import com.ecore.squad.model.role.Role;
import com.ecore.squad.model.role.RoleDto;
import com.ecore.squad.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByName(@PathVariable(name = "name") String name) {
        Optional<Role> roleOptional = roleService.findRoleByName(name);
        if (roleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(roleOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewRole(@Valid @RequestBody RoleDto roleDto) {
        Role role = roleService.saveNewRole(roleDto);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(role.getName())
                        .toUri()).body(role);
    }
}
