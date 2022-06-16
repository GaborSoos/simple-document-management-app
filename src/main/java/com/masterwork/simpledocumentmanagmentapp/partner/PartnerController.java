package com.masterwork.simpledocumentmanagmentapp.partner;

import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerReqDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerResDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnersDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Partner controller provide CRUD operations to Partner resource", tags = {"Partners"})
@PreAuthorize("hasAnyRole({'ADMIN', 'USER'})")
@RestController
@RequestMapping("/partner")
public class PartnerController {

  private final PartnerService partnerService;

  public PartnerController(PartnerService partnerService) {
    this.partnerService = partnerService;
  }

  @ApiOperation(value = "Get all partners")
  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @GetMapping
  public ResponseEntity<PartnersDto> getAll() {
    return ResponseEntity.status(200).body(partnerService.findAll());
  }

  @ApiOperation(value = "Get a given partner by name")
  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @GetMapping("/{partnerName}")
  public ResponseEntity<PartnerResDto> getByPartnerName(@PathVariable String partnerName) {
    return ResponseEntity.status(200).body(partnerService.convertToDto(partnerService.findByName(partnerName)));
  }

  @ApiOperation(value = "Create new partner")
  @PostMapping
  public ResponseEntity<PartnerResDto> createNewRole(@Valid @RequestBody PartnerReqDto partnerReqDto) {
    return ResponseEntity.status(201).body(partnerService.createPartner(partnerReqDto));
  }

  @ApiOperation(value = "Modify a given partner by name")
  @PutMapping("/{partnerName}")
  public ResponseEntity<PartnerResDto> modifyPartnerBaseData(@PathVariable String partnerName,
                                                       @RequestBody PartnerReqDto partnerReqDto) {
    partnerReqDto.setName(partnerName);
    return ResponseEntity.status(200).body(partnerService.modifyPartnerBaseData(partnerReqDto));
  }

  @ApiOperation(value = "Delete a given partner by name")
  @DeleteMapping("/{partnerName}")
  public ResponseEntity<InfoDto> deletePartnerByName(@PathVariable String partnerName) {
    partnerService.deletePartner(partnerName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following partner: " + partnerName));
  }

}
