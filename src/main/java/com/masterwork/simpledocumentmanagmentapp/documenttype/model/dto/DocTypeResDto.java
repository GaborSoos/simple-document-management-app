package com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "DocumentType model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocTypeResDto {

  @ApiModelProperty(value = "DocumentType name", example = "invoice")
  private String name;

}
