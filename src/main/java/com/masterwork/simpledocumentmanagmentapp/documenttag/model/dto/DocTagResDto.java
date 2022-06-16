package com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "DocumentTag model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocTagResDto {

  @ApiModelProperty(value = "DocumentTag name", example = "finance-document")
  private String name;

}
