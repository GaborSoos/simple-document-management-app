package com.masterwork.simpledocumentmanagmentapp.documenttag;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagsDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctag")
public class DocTagController {

  private final DocTagService docTagService;

  public DocTagController(DocTagService docTagService) {
    this.docTagService = docTagService;
  }

  @GetMapping
  public ResponseEntity<DocTagsDto> findAll() {
    return ResponseEntity.status(200).body(docTagService.findAll());
  }

  @PostMapping
  public ResponseEntity<DocTagResDto> createNewDocTag(@Valid @RequestBody DocTagReqDto docTagReqDto) {
    return ResponseEntity.status(201).body(docTagService.createDocTag(docTagReqDto));
  }

  @DeleteMapping("/{docTagName}")
  public ResponseEntity<InfoDto> deleteDocTagByName(@PathVariable String docTagName) {
    docTagService.deleteDocTag(docTagName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following document tag: " + docTagName));
  }

}
