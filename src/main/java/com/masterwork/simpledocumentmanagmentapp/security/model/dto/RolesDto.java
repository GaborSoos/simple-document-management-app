package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RolesDto {

  private List<RoleDto> roleDtos = new ArrayList<>();

}
