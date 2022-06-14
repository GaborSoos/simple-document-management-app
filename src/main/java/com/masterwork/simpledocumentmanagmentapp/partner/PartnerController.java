package com.masterwork.simpledocumentmanagmentapp.partner;
;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerReqDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerResDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnersDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/partner")
public class PartnerController {

  private final PartnerService partnerService;

  public PartnerController(PartnerService partnerService) {
    this.partnerService = partnerService;
  }

  @GetMapping
  public ResponseEntity<PartnersDto> getAll() {
    return ResponseEntity.status(200).body(partnerService.findAll());
  }

  @GetMapping("/{partnerName}")
  public ResponseEntity<PartnerResDto> getByPartnerName(@PathVariable String partnerName) {
    return ResponseEntity.status(200).body(partnerService.convertToDto(partnerService.findByName(partnerName)));
  }

  @PostMapping
  public ResponseEntity<PartnerResDto> createNewRole(@Valid @RequestBody PartnerReqDto partnerReqDto) {
    return ResponseEntity.status(201).body(partnerService.createPartner(partnerReqDto));
  }

  @PutMapping("/{partnerName}")
  public ResponseEntity<PartnerResDto> modifyPartnerBaseData(@PathVariable String partnerName,
                                                       @RequestBody PartnerReqDto partnerReqDto) {
    // Todo: mivan ha nevet akar változtatni???
    partnerReqDto.setName(partnerName);
    return ResponseEntity.status(200).body(partnerService.modifyPartnerBaseData(partnerReqDto));
  }

  @DeleteMapping("/{partnerName}")
  public ResponseEntity<InfoDto> deletePartnerByName(@PathVariable String partnerName) {
    partnerService.deletePartner(partnerName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following partner: " + partnerName));
  }

}
