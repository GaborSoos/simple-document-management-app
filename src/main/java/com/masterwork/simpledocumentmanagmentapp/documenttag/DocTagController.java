package com.masterwork.simpledocumentmanagmentapp.documenttag;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagsDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "DocTag controller provide CRUD operations to DocumentTag resource", tags = {"DocumentTags"})
@PreAuthorize("hasAnyRole({'ADMIN', 'USER'})")
@RestController
@RequestMapping("/doctag")
public class DocTagController {

  private final DocTagService docTagService;

  public DocTagController(DocTagService docTagService) {
    this.docTagService = docTagService;
  }

  @ApiOperation(value = "Get all documentTags")
  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @GetMapping
  public ResponseEntity<DocTagsDto> findAll() {
    return ResponseEntity.status(200).body(docTagService.findAll());
  }

  @ApiOperation(value = "Create new documentTag")
  @PostMapping
  public ResponseEntity<DocTagResDto> createNewDocTag(@Valid @RequestBody DocTagReqDto docTagReqDto) {
    return ResponseEntity.status(201).body(docTagService.createDocTag(docTagReqDto));
  }

  @ApiOperation(value = "Delete a given documentTag by name")
  @DeleteMapping("/{docTagName}")
  public ResponseEntity<InfoDto> deleteDocTagByName(@PathVariable String docTagName) {
    docTagService.deleteDocTag(docTagName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following document tag: " + docTagName));
  }

}
