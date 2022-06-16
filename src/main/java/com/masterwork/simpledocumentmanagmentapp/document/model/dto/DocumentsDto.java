package com.masterwork.simpledocumentmanagmentapp.document.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "DocumentTag model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentsDto {

  @ApiModelProperty(value = "DocumentResDto model list")
  private List<DocumentResDto> documentResDtos = new ArrayList<>();

}
