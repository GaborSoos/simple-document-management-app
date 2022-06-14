package com.masterwork.simpledocumentmanagmentapp.document;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentReqDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/document")
public class DocumentController {

  private final DocumentService documentService;

  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @GetMapping("/{docId}")
  public ResponseEntity<DocumentResDto> getById(@PathVariable Long docId) {
    return ResponseEntity.status(200).body(documentService.convertToDto(documentService.findById(docId)));
  }

  @GetMapping
  public ResponseEntity<DocumentsDto> getAll(@RequestParam(value = "partnerName", required = false) String partnerName,
                                             @RequestParam(value = "docTypeName", required = false) String docTypeName,
                                             @RequestParam(value = "docTagName", required = false) String docTagName,
                                             @RequestParam(value = "from", required = false) LocalDate from,
                                             @RequestParam(value = "to", required = false) LocalDate to) {
    return ResponseEntity.status(200).body(documentService.findAllByParam(partnerName, docTypeName, docTagName, from, to));
  }

  @PostMapping
  public ResponseEntity<DocumentResDto> createNewDocument(@Valid @RequestBody DocumentReqDto documentReqDto) {
    return ResponseEntity.status(201).body(documentService.createDocument(documentReqDto));
  }

  @PutMapping("/{docId}")
  public ResponseEntity<DocumentResDto> modifyDocumentBaseData(@PathVariable Long docId,
                                                              @RequestBody DocumentReqDto documentReqDto) {
    return ResponseEntity.status(200).body(documentService.modifyDocumentBaseData(docId, documentReqDto));
  }

  @DeleteMapping("/{docId}")
  public ResponseEntity<InfoDto> deleteDocumentById(@PathVariable Long docId) {
    documentService.deleteById(docId);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following document with id: " + docId));
  }

  @PostMapping("/{docId}/docTag/")
  public ResponseEntity<DocumentResDto> addTag(@PathVariable Long docId, @RequestBody DocTagReqDto docTagReqDto) {
    return ResponseEntity.status(200).body(documentService.addTag(docId, docTagReqDto));
  }

  @DeleteMapping("/{docId}/docTag/{docTagName}")
  public ResponseEntity<InfoDto> deleteTag(@PathVariable Long docId, @PathVariable String docTagName) {
    documentService.deleteTag(docId, docTagName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following documentTag: " + docTagName));
  }

}
