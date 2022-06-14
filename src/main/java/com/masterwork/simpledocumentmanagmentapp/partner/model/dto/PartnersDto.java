package com.masterwork.simpledocumentmanagmentapp.partner.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PartnersDto {

  private List<PartnerResDto> partnerResDtos = new ArrayList<>();

}
