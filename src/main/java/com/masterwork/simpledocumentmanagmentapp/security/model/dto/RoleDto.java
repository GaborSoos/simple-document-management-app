package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Role information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

  @ApiModelProperty(value = "Role name", example = "ADMIN")
  private String name;
  @ApiModelProperty(value = "Enabled status", example = "true")
  private Boolean enabled;

}
