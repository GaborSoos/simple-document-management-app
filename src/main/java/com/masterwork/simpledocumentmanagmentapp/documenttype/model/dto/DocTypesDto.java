package com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "DocumentType list for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocTypesDto {

  @ApiModelProperty(value = "Documentype list")
  private List<DocTypeResDto> docTypeResDtos = new ArrayList<>();

}
