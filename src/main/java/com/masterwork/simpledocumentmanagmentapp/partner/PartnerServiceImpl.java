package com.masterwork.simpledocumentmanagmentapp.partner;

import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerReqDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerResDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnersDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

  private final PartnerRepository partnerRepository;

  public PartnerServiceImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  public PartnersDto findAll() {
    List<Partner> partners = partnerRepository.findAll();
    if(partners.isEmpty()) {
      throw new RequestedResourceNotFoundException("Requested resource is not found");
    } else {
      return convertToDtoList(partners);
    }
  }

  @Override
  public PartnerResDto convertToDto(Partner partner) {
    return new PartnerResDto(
        partner.getName(),
        partner.getAddress(),
        partner.getEmail(),
        partner.getTelephone(),
        partner.getEnabled()
    );
  }

  @Override
  public PartnersDto convertToDtoList(List<Partner> partners) {
    PartnersDto partnersDto = new PartnersDto();
    partners.forEach(partner -> partnersDto.getPartnerResDtos().add(convertToDto(partner)));
    return partnersDto;
  }

  @Override
  public Partner findByName(String partnerName) {
    return partnerRepository.findByName(partnerName)
        .orElseThrow(() -> new RequestedResourceNotFoundException("Requested resource is not found"));
  }

  @Override
  public PartnerResDto createPartner(PartnerReqDto partnerReqDto) {
    if(partnerRepository.existsPartnerByName(partnerReqDto.getName())) {
      throw new RequestCauseConflictException("Partner is (" + partnerReqDto.getName() + ") already exist.");
    } else {
      return convertToDto(
          partnerRepository.save(
              new Partner(
                  partnerReqDto.getName(),
                  partnerReqDto.getAddress(),
                  partnerReqDto.getEmail(),
                  partnerReqDto.getTelephone(),
                  partnerReqDto.getEnabled()
              )
          )
      );
    }
  }

  @Override
  public PartnerResDto modifyPartnerBaseData(PartnerReqDto partnerReqDto) {
    Partner partner = findByName(partnerReqDto.getName());
    partner.setName(partnerReqDto.getName());
    partner.setAddress(partnerReqDto.getAddress());
    partner.setEmail(partnerReqDto.getEmail());
    partner.setTelephone(partnerReqDto.getTelephone());
    partner.setEnabled(partnerReqDto.getEnabled());
    return convertToDto(partnerRepository.save(partner));
  }

  @Override
  public void deletePartner(String partnerName) {
    if(!partnerRepository.existsPartnerByName(partnerName)) {
      throw new RequestedResourceNotFoundException("Partner (" + partnerName + ") is not exist.");
    }
    partnerRepository.deleteByName(partnerName);
  }

}
