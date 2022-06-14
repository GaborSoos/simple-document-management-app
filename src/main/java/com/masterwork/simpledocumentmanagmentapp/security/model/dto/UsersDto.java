package com.masterwork.simpledocumentmanagmentapp.security.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsersDto {

  private List<UserResDto> users = new ArrayList<>();

}
