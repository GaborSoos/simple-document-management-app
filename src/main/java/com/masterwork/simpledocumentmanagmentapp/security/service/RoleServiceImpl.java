package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RolesDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;
import com.masterwork.simpledocumentmanagmentapp.security.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

  private final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findByName(String roleName) {
    return roleRepository.findByName(roleName)
        .orElseThrow(() -> new RequestedResourceNotFoundException("Requested resource is not found"));
  }

  @Override
  public RolesDto findAll() {
    List<Role> roles = roleRepository.findAll();
    if (roles.isEmpty()) {
      throw new RequestedResourceNotFoundException("Requested resource is not found");
    } else {
      return convertToDtoList(roles);
    }
  }

  @Override
  public RoleDto modifyEnabledStatus(RoleDto roleDto) {
    Role role = findByName(roleDto.getName());
    role.setEnabled(roleDto.getEnabled());
    return convertToDto(roleRepository.save(role));
  }

  @Override
  public RoleDto createRole(RoleDto roleDto) {
    return convertToDto(roleRepository.save(new Role(roleDto.getName(), roleDto.getEnabled())));
  }

  @Override
  public void deleteRole(String roleName) {
    roleRepository.deleteByName(roleName);
  }

  @Override
  public RoleDto convertToDto(Role role) {
    return new RoleDto(role.getName(), role.getEnabled());
  }

  @Override
  public RolesDto convertToDtoList(List<Role> roles) {
    RolesDto rolesDto = new RolesDto();
    roles.forEach(role -> rolesDto.getRoleDtos().add(convertToDto(role)));
    return rolesDto;
  }

}
