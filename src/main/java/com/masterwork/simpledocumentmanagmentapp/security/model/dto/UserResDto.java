package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserResDto {

  private String userName;
  private String firstName;
  private String lastName;
  private Boolean enabled;
  private List<String> roles = new ArrayList<>();

}
