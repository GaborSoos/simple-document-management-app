package com.masterwork.simpledocumentmanagmentapp.documenttype;

import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypesDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctype")
public class DocTypeController {

  private final DocTypeService docTypeService;

  public DocTypeController(DocTypeService docTypeService) {
    this.docTypeService = docTypeService;
  }

  @GetMapping
  public ResponseEntity<DocTypesDto> getAll() {
    return ResponseEntity.status(200).body(docTypeService.findAll());
  }

  @GetMapping("/{docTypeName}")
  public ResponseEntity<DocTypeResDto> getByDocTypeName(@PathVariable String docTypeName) {
    return ResponseEntity.status(200).body(docTypeService.convertToDto(docTypeService.findByName(docTypeName)));
  }

  @PostMapping
  public ResponseEntity<DocTypeResDto> createNewDocType(@Valid @RequestBody DocTypeReqDto docTypeReqDto) {
    return ResponseEntity.status(201).body(docTypeService.createDocType(docTypeReqDto));
  }

  @PutMapping("/{docTypeName}")
  public ResponseEntity<DocTypeResDto> modifyDocType(@PathVariable String docTypeName,
                                                             @RequestBody DocTypeReqDto docTypeReqDto) {
    // Todo: mivan ha nevet akar változtatni???
    docTypeReqDto.setName(docTypeName);
    return ResponseEntity.status(200).body(docTypeService.modifyDocTypeName(docTypeReqDto));
  }

  @DeleteMapping("/{docTypeName}")
  public ResponseEntity<InfoDto> deleteDocTypeByName(@PathVariable String docTypeName) {
    docTypeService.deleteDocType(docTypeName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following document type: " + docTypeName));
  }

}
