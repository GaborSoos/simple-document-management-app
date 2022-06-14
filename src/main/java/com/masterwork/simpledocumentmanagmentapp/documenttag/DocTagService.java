package com.masterwork.simpledocumentmanagmentapp.documenttag;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagsDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;

import java.util.List;

public interface DocTagService {

  DocumentTag findByName(String docTagName);

  DocTagsDto findAll();

  DocTagResDto convertToDto(DocumentTag documentTag);

  DocTagsDto convertToDtoList(List<DocumentTag> documentTags);

  DocTagResDto createDocTag(DocTagReqDto docTagReqDto);

  void deleteDocTag(String docTagName);
}
