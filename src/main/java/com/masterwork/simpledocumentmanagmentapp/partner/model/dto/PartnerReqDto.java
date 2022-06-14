package com.masterwork.simpledocumentmanagmentapp.partner.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerReqDto {

  @NotBlank(message = "Name is required.")
  private String name;
  @NotBlank(message = "Address is required.")
  private String address;
  @NotBlank(message = "Email is required.")
  private String email;
  @NotBlank(message = "Telephone is required.")
  private String telephone;
  private Boolean enabled;

}
