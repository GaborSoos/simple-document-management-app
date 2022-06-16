package com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "DocumentTag list for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocTagsDto {

  @ApiModelProperty(value = "DocumentTag list")
  private List<DocTagResDto> docTagResDtos = new ArrayList<>();

}
