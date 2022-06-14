package com.masterwork.simpledocumentmanagmentapp.partner;

import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerReqDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerResDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnersDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;

import java.util.List;

public interface PartnerService {
  
  PartnersDto findAll();

  PartnerResDto convertToDto(Partner partner);

  PartnersDto convertToDtoList(List<Partner> partners);

  Partner findByName(String partnerName);

  PartnerResDto createPartner(PartnerReqDto partnerReqDto);

  PartnerResDto modifyPartnerBaseData(PartnerReqDto partnerReqDto);

  void deletePartner(String partnerName);
}
