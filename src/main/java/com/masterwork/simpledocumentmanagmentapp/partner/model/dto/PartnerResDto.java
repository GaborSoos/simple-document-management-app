package com.masterwork.simpledocumentmanagmentapp.partner.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "Partner model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PartnerResDto {

  @ApiModelProperty(value = "Partner name", example = "Google")
  private String name;
  @ApiModelProperty(value = "Address of partner", example = "1111 Budapest, Kereső utca 1.")
  private String address;
  @ApiModelProperty(value = "Email address", example = "info@google.com")
  private String email;
  @ApiModelProperty(value = "Telephone", example = "06-1-123-4567")
  private String telephone;
  @ApiModelProperty(value = "Enabled status", example = "true")
  private Boolean enabled;

}
