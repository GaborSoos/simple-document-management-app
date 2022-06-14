package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RolesDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;

import java.util.List;

public interface RoleService {

  Role findByName(String roleName);

  RolesDto findAll();

  RoleDto modifyEnabledStatus(RoleDto roleDto);

  RoleDto createRole(RoleDto roleDto);

  void deleteRole(String roleName);

  RoleDto convertToDto(Role role);

  RolesDto convertToDtoList(List<Role> roles);

}
