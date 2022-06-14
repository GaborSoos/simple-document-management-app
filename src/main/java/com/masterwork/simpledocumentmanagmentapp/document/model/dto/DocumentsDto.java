package com.masterwork.simpledocumentmanagmentapp.document.model.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentsDto {

  private List<DocumentResDto> documentResDtos = new ArrayList<>();

}
