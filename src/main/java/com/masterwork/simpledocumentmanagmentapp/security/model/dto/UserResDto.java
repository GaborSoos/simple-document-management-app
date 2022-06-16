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
public class UserResDto {

  @ApiModelProperty(example = "johndoe")
  private String userName;
  @ApiModelProperty(example = "John")
  private String firstName;
  @ApiModelProperty(example = "Doe")
  private String lastName;
  @ApiModelProperty(example = "true")
  private Boolean enabled;
  @ApiModelProperty(value = "List of role names")
  private List<String> roles = new ArrayList<>();

}
