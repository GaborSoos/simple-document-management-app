package com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocTagsDto {

  private List<DocTagResDto> docTagResDtos = new ArrayList<>();

}
