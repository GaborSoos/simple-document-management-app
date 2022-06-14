package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {

  @NotBlank(message = "Username is required.")
  private String userName;
  @NotBlank(message = "Firstname is required.")
  private String firstName;
  @NotBlank(message = "Lastname is required.")
  private String lastName;
  @Size(min = 8, message = "Password must be at least 8 characters.")
  private String password;
  private Boolean enabled = false;

}
