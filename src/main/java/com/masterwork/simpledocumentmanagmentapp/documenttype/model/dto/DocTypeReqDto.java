package com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "DocumentType model information from client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocTypeReqDto {

  @ApiModelProperty(value = "DocumentType name", example = "invoice")
  @NotBlank(message = "Name is required.")
  private String name;

}
