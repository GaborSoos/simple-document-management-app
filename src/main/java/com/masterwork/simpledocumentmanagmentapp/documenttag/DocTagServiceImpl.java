package com.masterwork.simpledocumentmanagmentapp.documenttag;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagsDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocTagServiceImpl implements DocTagService {

  private final DocTagRepository docTagRepository;

  public DocTagServiceImpl(DocTagRepository docTagRepository) {
    this.docTagRepository = docTagRepository;
  }

  @Override
  public DocumentTag findByName(String docTagName) {
    return docTagRepository.findByName(docTagName)
        .orElseThrow(() -> new RequestedResourceNotFoundException("Requested resource is not found"));
  }

  @Override
  public DocTagsDto findAll() {
    List<DocumentTag> documentTags = docTagRepository.findAll();
    if(documentTags.isEmpty()) {
      throw new RequestedResourceNotFoundException("Requested resource is not found");
    }
    return convertToDtoList(documentTags);
  }

  @Override
  public DocTagResDto createDocTag(DocTagReqDto docTagReqDto) {
    if(docTagRepository.existsDocumentTagByName(docTagReqDto.getName())) {
      throw new RequestCauseConflictException("DocumentTag is (" + docTagReqDto.getName() + ") already exist.");
    } else {
      return convertToDto(docTagRepository.save(new DocumentTag(docTagReqDto.getName())));
    }
  }

  @Override
  public void deleteDocTag(String docTagName) {
    if(!docTagRepository.existsDocumentTagByName(docTagName)) {
      throw new RequestedResourceNotFoundException("DocumentTag (" + docTagName + ") is not exist.");
    }
    docTagRepository.delete(findByName(docTagName));

  }

  @Override
  public DocTagResDto convertToDto(DocumentTag documentTag) {
    return new DocTagResDto(documentTag.getName());
  }

  @Override
  public DocTagsDto convertToDtoList(List<DocumentTag> documentTags) {
    DocTagsDto docTagsDto = new DocTagsDto();
    documentTags.forEach(documentTag -> docTagsDto.getDocTagResDtos().add(convertToDto(documentTag)));
    return docTagsDto;
  }

}
