package com.masterwork.simpledocumentmanagmentapp.partner.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Partner model information from client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerReqDto {

  @ApiModelProperty(value = "Partner name", example = "Google")
  @NotBlank(message = "Name is required.")
  private String name;
  @ApiModelProperty(value = "Address of partner", example = "1111 Budapest, Kereső utca 1.")
  @NotBlank(message = "Address is required.")
  private String address;
  @ApiModelProperty(value = "Email address", example = "info@google.com")
  @NotBlank(message = "Email is required.")
  private String email;
  @ApiModelProperty(value = "Telephone", example = "06-1-123-4567")
  @NotBlank(message = "Telephone is required.")
  private String telephone;
  @ApiModelProperty(value = "Enabled status", example = "true")
  private Boolean enabled;

}
