package com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "DocumentTag model information from client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocTagReqDto {

  @ApiModelProperty(value = "DocumentTag name", example = "finance-document")
  @NotBlank(message = "Name is required.")
  private String name;

}
