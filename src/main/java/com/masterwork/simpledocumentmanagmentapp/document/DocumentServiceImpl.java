package com.masterwork.simpledocumentmanagmentapp.document;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentReqDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import com.masterwork.simpledocumentmanagmentapp.documenttag.DocTagService;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
import com.masterwork.simpledocumentmanagmentapp.documenttype.DocTypeService;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.partner.PartnerService;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

  private final DocumentRepository documentRepository;
  private final PartnerService partnerService;
  private final DocTypeService docTypeService;
  private final DocTagService docTagService;

  public DocumentServiceImpl(DocumentRepository documentRepository, PartnerService partnerService,
                             DocTypeService docTypeService, DocTagService docTagService) {
    this.documentRepository = documentRepository;
    this.partnerService = partnerService;
    this.docTypeService = docTypeService;
    this.docTagService = docTagService;
  }

  @Override
  public Document findById(Long id) {
    return documentRepository.findById(id).orElseThrow(
        () -> new RequestedResourceNotFoundException("Requested resource is not found: " + id + " (documentId)" ));
  }

  @Override
  public DocumentsDto findAllByParam(String partnerName, String docTypeName, String docTagName, LocalDate from, LocalDate to) {
    List<Document> documents = documentRepository.findAllByParam(partnerName, docTypeName, docTagName, from, to);
    if(documents.isEmpty()) {
      throw new RequestedResourceNotFoundException("Requested resource is not found");
    } else {
      return convertToDtoList(documents);
    }
  }

  @Override
  public DocumentResDto createDocument(DocumentReqDto documentReqDto) {
    Partner partner = partnerService.findByName(documentReqDto.getPartnerName());
    if(documentRepository.existsDocumentByOriginalDocumentIdAndPartner(documentReqDto.getOriginalDocumentId(), partner)) {
      throw new RequestCauseConflictException("Document with the following data - partner: " + partner.getName()
          + ", original document id: " + documentReqDto.getOriginalDocumentId() + " - already exist.");
    }
    DocumentType documentType = docTypeService.findByName(documentReqDto.getDocumentTypeName());
    Document document = new Document(documentReqDto.getOriginalDocumentId(), documentType, documentReqDto.getSubject(),
        documentReqDto.getDescription(),documentReqDto.getOriginalDocumentDate(), partner, documentReqDto.getEnabled()
    );
    return convertToDto(documentRepository.save(document));
  }

  @Override
  public DocumentResDto modifyDocumentBaseData(Long docId, DocumentReqDto documentReqDto) {
    Document document = findById(docId);
    Partner partner = partnerService.findByName(documentReqDto.getPartnerName());
    DocumentType documentType = docTypeService.findByName(documentReqDto.getDocumentTypeName());
    document.setOriginalDocumentId(documentReqDto.getOriginalDocumentId());
    document.setDocumentType(documentType);
    document.setSubject(documentReqDto.getSubject());
    document.setDescription(documentReqDto.getDescription());
    document.setOriginalDocumentDate(documentReqDto.getOriginalDocumentDate());
    document.setPartner(partner);
    document.setEnabled(documentReqDto.getEnabled());
    return convertToDto(documentRepository.save(document));
  }

  @Override
  public void deleteById(Long id) {
    findById(id);
    documentRepository.deleteById(id);
  }

  @Override
  public DocumentResDto addTag(Long docId, DocTagReqDto docTagReqDto) {
    Document document = findById(docId);
    DocumentTag documentTag = docTagService.findByName(docTagReqDto.getName());
    document.getDocumentTags().add(documentTag);
    return convertToDto(documentRepository.save(document));
  }

  @Override
  public void deleteTag(Long docId, String docTagName) {
    Document document = findById(docId);
    DocumentTag documentTag = docTagService.findByName(docTagName);
    document.getDocumentTags().remove(documentTag);
    documentRepository.save(document);
  }

  @Override
  public DocumentResDto convertToDto(Document document) {
    return new DocumentResDto(
        document.getId(),
        document.getOriginalDocumentId(),
        document.getDocumentType().getName(),
        document.getSubject(),
        document.getDescription(),
        document.getOriginalDocumentDate(),
        document.getPartner().getName(),
        document.getDocumentTags().stream().map(t -> t.getName()).collect(Collectors.toList()),
        document.getEnabled()
    );
  }

  @Override
  public DocumentsDto convertToDtoList(List<Document> documents) {
    DocumentsDto documentsDto = new DocumentsDto();
    documents.forEach(d -> documentsDto.getDocumentResDtos().add(convertToDto(d)));
    return documentsDto;
  }

}
