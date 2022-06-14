package com.masterwork.simpledocumentmanagmentapp.documenttype;

import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypesDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;

import java.util.List;

public interface DocTypeService {

  DocTypesDto findAll();

  DocTypeResDto convertToDto(DocumentType documentType);

  DocTypesDto convertToDtoList(List<DocumentType> documentTypes);

  DocumentType findByName(String docTypeName);

  DocTypeResDto createDocType(DocTypeReqDto docTypeReqDto);

  DocTypeResDto modifyDocTypeName(DocTypeReqDto docTypeReqDto);

  void deleteDocType(String docTypeName);
}
