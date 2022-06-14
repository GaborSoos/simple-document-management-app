package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDto {

  @NotBlank(message = "Username is required.")
  private String userName;
  @NotBlank(message = "Password is required.")
  private String password;

}
