package com.masterwork.simpledocumentmanagmentapp.partner.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PartnerResDto {

  private String name;
  private String address;
  private String email;
  private String telephone;
  private Boolean enabled;

}
