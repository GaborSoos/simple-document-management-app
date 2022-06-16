package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(description = "User model information from client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {

  @ApiModelProperty(example = "johndoe")
  @NotBlank(message = "Username is required.")
  private String userName;
  @ApiModelProperty(example = "John")
  @NotBlank(message = "Firstname is required.")
  private String firstName;
  @ApiModelProperty(example = "Doe")
  @NotBlank(message = "Lastname is required.")
  private String lastName;
  @ApiModelProperty(example = "veryverysecret")
  @Size(min = 8, message = "Password must be at least 8 characters.")
  private String password;
  @ApiModelProperty(example = "true")
  private Boolean enabled = false;

}
