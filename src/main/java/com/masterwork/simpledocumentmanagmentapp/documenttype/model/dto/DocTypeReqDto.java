package com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocTypeReqDto {

  @NotBlank(message = "Name is required.")
  private String name;

}
