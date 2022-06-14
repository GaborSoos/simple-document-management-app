package com.masterwork.simpledocumentmanagmentapp;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentReqDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;

import java.time.LocalDate;
import java.util.List;

public class TestUtils {

  public static Document defaultDocument() {
    return documentBuilder().build();
  }

  public static DocumentReqDto defaultDocumentReqDto() {
    return documentReqDtoBuilder().build();
  }

  public static DocumentResDto defaultDocumentResDto() {
    return documentResDtoBuilder().build();
  }

  public static DocumentsDto defaultDocumentsDto() { return documentsDtoBuilder().build(); }

  public static Partner defaultPartner() {
    return partnerBuilder().build();
  }

  public static DocumentBuilder documentBuilder() {
    return new DocumentBuilder()
        .withId(null)
        .withOriginalDocumentId(null)
        .withDocumentType(null)
        .withSubject(null)
        .withDescription(null)
        .withOriginalDocumentDate(null)
        .withPartner(null)
        .withEnabled(null)
        .withDocumentTags(null);
  }

  public static DocumentReqDtoBuilder documentReqDtoBuilder() {
    return new DocumentReqDtoBuilder()
        .withOriginalDocumentId(null)
        .withDocumentTypeName(null)
        .withSubject(null)
        .withDescription(null)
        .withOriginalDocumentDate(null)
        .withPartnerName(null)
        .withEnabled(null);
  }

  public static DocumentResDtoBuilder documentResDtoBuilder() {
    return new DocumentResDtoBuilder()
        .withId(null)
        .withOriginalDocumentId(null)
        .withDocumentTypeName(null)
        .withSubject(null)
        .withDescription(null)
        .withOriginalDocumentDate(null)
        .withPartnerName(null)
        .withDocTags(null)
        .withEnabled(null);
  }

  public static DocumentsDtoBuilder documentsDtoBuilder() {
    return new DocumentsDtoBuilder()
        .withDocumentResDtos(null);
  }

  public static PartnerBuilder partnerBuilder() {
    return new PartnerBuilder()
        .withId(null)
        .withName(null)
        .withAddress(null)
        .withEmail(null)
        .withTelephone(null)
        .withEnabled(null)
        .withDocuments(null);
  }

  public static class DocumentBuilder {
    private Document document;

    DocumentBuilder() {
      document = new Document();
    }

    DocumentBuilder(Document document) {
      this.document = document;
    }

    public DocumentBuilder withId(Long id) {
      document.setId(id);
      return this;
    }

    public DocumentBuilder withOriginalDocumentId(String originalDocumentId) {
      document.setOriginalDocumentId(originalDocumentId);
      return this;
    }

    public DocumentBuilder withDocumentType(DocumentType documentType) {
      document.setDocumentType(documentType);
      return this;
    }

    public DocumentBuilder withSubject(String subject) {
      document.setSubject(subject);
      return this;
    }

    public DocumentBuilder withDescription(String description) {
      document.setDescription(description);
      return this;
    }

    public DocumentBuilder withOriginalDocumentDate(LocalDate originalDocumentDate) {
      document.setOriginalDocumentDate(originalDocumentDate);
      return this;
    }

    public DocumentBuilder withPartner(Partner partner) {
      document.setPartner(partner);
      return this;
    }

    public DocumentBuilder withEnabled(Boolean enabled) {
      document.setEnabled(enabled);
      return this;
    }

    public DocumentBuilder withDocumentTags(List<DocumentTag> documentTags) {
      document.setDocumentTags(documentTags);
      return this;
    }

    public Document build() {
      return document;
    }
  }

  public static class DocumentReqDtoBuilder {
    private DocumentReqDto documentReqDto;

    DocumentReqDtoBuilder() {
      documentReqDto = new DocumentReqDto();
    }

    DocumentReqDtoBuilder(DocumentReqDto documentReqDto) {
      this.documentReqDto = documentReqDto;
    }

    public DocumentReqDtoBuilder withOriginalDocumentId(String originalDocumentId) {
      documentReqDto.setOriginalDocumentId(originalDocumentId);
      return this;
    }

    public DocumentReqDtoBuilder withDocumentTypeName(String documentTypeName) {
      documentReqDto.setDocumentTypeName(documentTypeName);
      return this;
    }

    public DocumentReqDtoBuilder withSubject(String subject) {
      documentReqDto.setSubject(subject);
      return this;
    }

    public DocumentReqDtoBuilder withDescription(String description) {
      documentReqDto.setDescription(description);
      return this;
    }

    public DocumentReqDtoBuilder withOriginalDocumentDate(LocalDate originalDocumentDate) {
      documentReqDto.setOriginalDocumentDate(originalDocumentDate);
      return this;
    }

    public DocumentReqDtoBuilder withPartnerName(String partnerName) {
      documentReqDto.setPartnerName(partnerName);
      return this;
    }

    public DocumentReqDtoBuilder withEnabled(Boolean enabled) {
      documentReqDto.setEnabled(enabled);
      return this;
    }

    public DocumentReqDto build() {
      return documentReqDto;
    }
  }

  public static class DocumentResDtoBuilder {
    private DocumentResDto documentResDto;

    DocumentResDtoBuilder() {
      documentResDto = new DocumentResDto();
    }

    DocumentResDtoBuilder(DocumentResDto documentResDto) {
      this.documentResDto = documentResDto;
    }

    public DocumentResDtoBuilder withId(Long id) {
      documentResDto.setId(id);
      return this;
    }

    public DocumentResDtoBuilder withOriginalDocumentId(String originalDocumentId) {
      documentResDto.setOriginalDocumentId(originalDocumentId);
      return this;
    }

    public DocumentResDtoBuilder withDocumentTypeName(String documentTypeName) {
      documentResDto.setDocumentTypeName(documentTypeName);
      return this;
    }

    public DocumentResDtoBuilder withSubject(String subject) {
      documentResDto.setSubject(subject);
      return this;
    }

    public DocumentResDtoBuilder withDescription(String description) {
      documentResDto.setDescription(description);
      return this;
    }

    public DocumentResDtoBuilder withOriginalDocumentDate(LocalDate originalDocumentDate) {
      documentResDto.setOriginalDocumentDate(originalDocumentDate);
      return this;
    }

    public DocumentResDtoBuilder withPartnerName(String partnerName) {
      documentResDto.setPartnerName(partnerName);
      return this;
    }

    public DocumentResDtoBuilder withEnabled(Boolean enabled) {
      documentResDto.setEnabled(enabled);
      return this;
    }

    public DocumentResDtoBuilder withDocTags(List<String> docTags) {
      documentResDto.setDocTags(docTags);
      return this;
    }

    public DocumentResDto build() {
      return documentResDto;
    }
  }

  public static class DocumentsDtoBuilder {
    private DocumentsDto documentsDto;

    DocumentsDtoBuilder() {
      documentsDto = new DocumentsDto();
    }

    DocumentsDtoBuilder(DocumentsDto documentsDto) {
      this.documentsDto = documentsDto;
    }

    public DocumentsDtoBuilder withDocumentResDtos(List<DocumentResDto> documentResDtos) {
      documentsDto.setDocumentResDtos(documentResDtos);
      return this;
    }

    public DocumentsDto build() {
      return documentsDto;
    }
  }

  public static class PartnerBuilder {
    private Partner partner;

    PartnerBuilder() {
      partner = new Partner();
    }

    PartnerBuilder(Partner partner) {
      this.partner = partner;
    }

    public PartnerBuilder withId(Long id) {
      partner.setId(id);
      return this;
    }

    public PartnerBuilder withName(String name) {
      partner.setName(name);
      return this;
    }

    public PartnerBuilder withAddress(String address) {
      partner.setAddress(address);
      return this;
    }

    public PartnerBuilder withEmail(String email) {
      partner.setEmail(email);
      return this;
    }

    public PartnerBuilder withTelephone(String telephone) {
      partner.setTelephone(telephone);
      return this;
    }

    public PartnerBuilder withEnabled(Boolean enabled) {
      partner.setEnabled(enabled);
      return this;
    }

    public PartnerBuilder withDocuments(List<Document> documents) {
      partner.setDocuments(documents);
      return this;
    }

    public Partner build() {
      return partner;
    }
  }
}
