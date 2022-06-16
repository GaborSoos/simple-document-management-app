package com.masterwork.simpledocumentmanagmentapp.documenttype;

import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypesDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "DocType controller provide CRUD operations to DocumentType resource", tags = {"DocumentTypes"})
@PreAuthorize("hasAnyRole({'ADMIN', 'USER'})")
@RestController
@RequestMapping("/doctype")
public class DocTypeController {

  private final DocTypeService docTypeService;

  public DocTypeController(DocTypeService docTypeService) {
    this.docTypeService = docTypeService;
  }

  @ApiOperation(value = "Get all documentTypes")
  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @GetMapping
  public ResponseEntity<DocTypesDto> getAll() {
    return ResponseEntity.status(200).body(docTypeService.findAll());
  }

  @ApiOperation(value = "Get a given documentType by name")
  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @GetMapping("/{docTypeName}")
  public ResponseEntity<DocTypeResDto> getByDocTypeName(@PathVariable String docTypeName) {
    return ResponseEntity.status(200).body(docTypeService.convertToDto(docTypeService.findByName(docTypeName)));
  }

  @ApiOperation(value = "Create new documentType")
  @PostMapping
  public ResponseEntity<DocTypeResDto> createNewDocType(@Valid @RequestBody DocTypeReqDto docTypeReqDto) {
    return ResponseEntity.status(201).body(docTypeService.createDocType(docTypeReqDto));
  }

  @ApiOperation(value = "Modify a given documentType by name")
  @PutMapping("/{docTypeName}")
  public ResponseEntity<DocTypeResDto> modifyDocType(@PathVariable String docTypeName,
                                                             @RequestBody DocTypeReqDto docTypeReqDto) {
    docTypeReqDto.setName(docTypeName);
    return ResponseEntity.status(200).body(docTypeService.modifyDocTypeName(docTypeReqDto));
  }

  @ApiOperation(value = "Delete a given documentType by name")
  @DeleteMapping("/{docTypeName}")
  public ResponseEntity<InfoDto> deleteDocTypeByName(@PathVariable String docTypeName) {
    docTypeService.deleteDocType(docTypeName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following document type: " + docTypeName));
  }

}
