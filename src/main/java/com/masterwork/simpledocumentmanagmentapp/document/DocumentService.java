package com.masterwork.simpledocumentmanagmentapp.document;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentReqDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;

import java.time.LocalDate;
import java.util.List;

public interface DocumentService {

  Document findById(Long id);

  DocumentsDto findAllByParam(String partnerName, String docTypeName, String docTagName, LocalDate from, LocalDate to);

  void deleteById(Long id);

  DocumentResDto convertToDto(Document document);

  DocumentsDto convertToDtoList(List<Document> documents);

  DocumentResDto createDocument(DocumentReqDto documentReqDto);

  DocumentResDto modifyDocumentBaseData(Long docId, DocumentReqDto documentReqDto);

  DocumentResDto addTag(Long docId, DocTagReqDto docTagReqDto);

  void deleteTag(Long docId, String docTagName);
}
