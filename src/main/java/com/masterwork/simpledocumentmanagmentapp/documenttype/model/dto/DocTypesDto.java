package com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocTypesDto {

  private List<DocTypeResDto> docTypeResDtos = new ArrayList<>();

}
