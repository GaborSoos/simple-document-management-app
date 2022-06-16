package com.masterwork.simpledocumentmanagmentapp.security.controller;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RolesDto;
import com.masterwork.simpledocumentmanagmentapp.security.service.RoleService;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(value = "Role controller provide CRUD operations to Role resource", tags = {"Roles"})
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin/role")
public class RoleController {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @ApiOperation(value = "Get all roles")
  @GetMapping
  public ResponseEntity<RolesDto> getAll() {
    return ResponseEntity.status(200).body(roleService.findAll());
  }

  @ApiOperation(value = "Get a given role by name")
  @GetMapping("/{roleName}")
  public ResponseEntity<RoleDto> getByRoleName(@PathVariable String roleName) {
    return ResponseEntity.status(200).body(roleService.convertToDto(roleService.findByName(roleName)));
  }

  @ApiOperation(value = "Create new role")
  @PostMapping
  public ResponseEntity<RoleDto> createNewRole(@RequestBody RoleDto roleDto) {
    return ResponseEntity.status(201).body(roleService.createRole(roleDto));
  }

  @ApiOperation(value = "Modify a given role by name")
  @PutMapping("/{roleName}")
  public ResponseEntity<RoleDto> modifyRoleEnabledStatus(@PathVariable String roleName, @RequestBody RoleDto roleDto) {
    roleDto.setName(roleName);
    return ResponseEntity.status(200).body(roleService.modifyEnabledStatus(roleDto));
  }

  @ApiOperation(value = "Delete a given role by name")
  @DeleteMapping("/{roleName}")
  public ResponseEntity<InfoDto> deleteRoleByName(@PathVariable String roleName) {
    roleService.deleteRole(roleName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of " + roleName + " role!"));
  }

}
