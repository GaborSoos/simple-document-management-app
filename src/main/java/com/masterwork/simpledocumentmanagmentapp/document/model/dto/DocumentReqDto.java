package com.masterwork.simpledocumentmanagmentapp.document.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReqDto {

  @NotBlank(message = "OriginalDocumentId is required.")
  private String originalDocumentId;
  @NotBlank(message = "DocumentTypeName is required.")
  private String documentTypeName;
  @NotBlank(message = "Subject is required.")
  private String subject;
  private String description;
  @NotNull
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDate originalDocumentDate;
  @NotBlank(message = "PartnerName is required.")
  private String partnerName;
  private Boolean enabled;

}
