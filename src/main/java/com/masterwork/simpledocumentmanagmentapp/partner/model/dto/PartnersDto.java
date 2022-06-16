package com.masterwork.simpledocumentmanagmentapp.partner.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Partner model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PartnersDto {

  @ApiModelProperty(value = "Partner list")
  private List<PartnerResDto> partnerResDtos = new ArrayList<>();

}
