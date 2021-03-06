package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {

  private String status;
  private String token;

}
