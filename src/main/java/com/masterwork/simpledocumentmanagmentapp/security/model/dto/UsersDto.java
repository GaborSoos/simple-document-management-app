package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "User model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsersDto {

  @ApiModelProperty(value = "List of userResDtos")
  private List<UserResDto> users = new ArrayList<>();

}
