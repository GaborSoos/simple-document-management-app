package com.masterwork.simpledocumentmanagmentapp.document.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentResDto {

  private Long id;
  private String originalDocumentId;
  private String documentTypeName;
  private String subject;
  private String description;
  private LocalDate originalDocumentDate;
  private String partnerName;
  private List<String> docTags;
  private Boolean enabled;


}
