package com.masterwork.simpledocumentmanagmentapp.documenttype;

import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypesDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocTypeServiceImpl implements DocTypeService{

  private final DocTypeRepository docTypeRepository;

  public DocTypeServiceImpl(DocTypeRepository docTypeRepository) {
    this.docTypeRepository = docTypeRepository;
  }

  @Override
  public DocTypesDto findAll() {
    List<DocumentType> documentTypes = docTypeRepository.findAll();
    if (documentTypes.isEmpty()) {
      throw new RequestedResourceNotFoundException("Requested resource is not found");
    } else {
      return convertToDtoList(documentTypes);
    }
  }

  @Override
  public DocumentType findByName(String docTypeName) {
    return docTypeRepository.findByName(docTypeName)
        .orElseThrow(() -> new RequestedResourceNotFoundException("Requested resource is not found"));
  }

  @Override
  public DocTypeResDto createDocType(DocTypeReqDto docTypeReqDto) {
    if(docTypeRepository.existsDocumentTypeByName(docTypeReqDto.getName())) {
      throw new RequestCauseConflictException("DocumentType is (" + docTypeReqDto.getName() + ") already exist.");
    } else {
      return convertToDto(docTypeRepository.save(new DocumentType(docTypeReqDto.getName())));
    }
  }

  @Override
  public DocTypeResDto modifyDocTypeName(DocTypeReqDto docTypeReqDto) {
    DocumentType documentType = findByName(docTypeReqDto.getName());
    documentType.setName(docTypeReqDto.getName());
    return convertToDto(docTypeRepository.save(documentType));
  }

  @Override
  public void deleteDocType(String docTypeName) {
    if(!docTypeRepository.existsDocumentTypeByName(docTypeName)) {
      throw new RequestedResourceNotFoundException("DocumentType (" + docTypeName + ") is not exist.");
    }
    docTypeRepository.deleteByName(docTypeName);
  }

  @Override
  public DocTypeResDto convertToDto(DocumentType documentType) {
    return new DocTypeResDto(documentType.getName());
  }

  @Override
  public DocTypesDto convertToDtoList(List<DocumentType> documentTypes) {
    DocTypesDto docTypesDto = new DocTypesDto();
    documentTypes.forEach(documentType -> docTypesDto.getDocTypeResDtos().add(convertToDto(documentType)));
    return docTypesDto;
  }

}
