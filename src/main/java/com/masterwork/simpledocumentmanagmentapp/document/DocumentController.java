package com.masterwork.simpledocumentmanagmentapp.document;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentReqDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(value = "Document controller provide CRUD operations to Document resource", tags = {"Documents"})
@PreAuthorize("hasAnyRole({'ADMIN', 'USER'})")
@RestController
@RequestMapping("/document")
public class DocumentController {

  private final DocumentService documentService;

  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @ApiOperation(value = "Get a given document by Id")
  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @GetMapping("/{docId}")
  public ResponseEntity<DocumentResDto> getById(@PathVariable Long docId) {
    return ResponseEntity.status(200).body(documentService.convertToDto(documentService.findById(docId)));
  }

  @PreAuthorize("hasAnyRole({'ADMIN', 'USER', 'GUEST'})")
  @ApiOperation(value = "Get all documents by param(s)")
  @GetMapping
  public ResponseEntity<DocumentsDto> getAll(@RequestParam(value = "partnerName", required = false) String partnerName,
                                             @RequestParam(value = "docTypeName", required = false) String docTypeName,
                                             @RequestParam(value = "docTagName", required = false) String docTagName,
                                             @RequestParam(value = "from", required = false) LocalDate from,
                                             @RequestParam(value = "to", required = false) LocalDate to) {
    return ResponseEntity.status(200).body(documentService.findAllByParam(partnerName, docTypeName, docTagName, from, to));
  }

  @ApiOperation(value = "Create new document")
  @PostMapping
  public ResponseEntity<DocumentResDto> createNewDocument(@Valid @RequestBody DocumentReqDto documentReqDto) {
    return ResponseEntity.status(201).body(documentService.createDocument(documentReqDto));
  }

  @ApiOperation(value = "Modify a given document by id")
  @PutMapping("/{docId}")
  public ResponseEntity<DocumentResDto> modifyDocumentBaseData(@PathVariable Long docId,
                                                              @RequestBody DocumentReqDto documentReqDto) {
    return ResponseEntity.status(200).body(documentService.modifyDocumentBaseData(docId, documentReqDto));
  }

  @ApiOperation(value = "Delete a given document by id")
  @DeleteMapping("/{docId}")
  public ResponseEntity<InfoDto> deleteDocumentById(@PathVariable Long docId) {
    documentService.deleteById(docId);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following document with id: " + docId));
  }

  @ApiOperation(value = "Add an existing document tag to an existing document")
  @PostMapping("/{docId}/docTag/")
  public ResponseEntity<DocumentResDto> addTag(@PathVariable Long docId, @RequestBody DocTagReqDto docTagReqDto) {
    return ResponseEntity.status(200).body(documentService.addTag(docId, docTagReqDto));
  }

  @ApiOperation(value = "Delete a document tag of a given document")
  @DeleteMapping("/{docId}/docTag/{docTagName}")
  public ResponseEntity<InfoDto> deleteTag(@PathVariable Long docId, @PathVariable String docTagName) {
    documentService.deleteTag(docId, docTagName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the following documentTag: " + docTagName));
  }

}
