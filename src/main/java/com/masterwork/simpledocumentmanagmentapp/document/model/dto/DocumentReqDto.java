package com.masterwork.simpledocumentmanagmentapp.document.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "DocumentTag model information from client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReqDto {

  @ApiModelProperty(value = "Original id of document", example = "INV/2022/000001")
  @NotBlank(message = "OriginalDocumentId is required.")
  private String originalDocumentId;
  @ApiModelProperty(value = "DocumentType name", example = "invoice")
  @NotBlank(message = "DocumentTypeName is required.")
  private String documentTypeName;
  @ApiModelProperty(value = "Subject of document", example = "Fuel invoice - 2022.01.")
  @NotBlank(message = "Subject is required.")
  private String subject;
  @ApiModelProperty(value = "Description of document", example = "This is a simple fuel invoice of a company car.")
  private String description;
  @ApiModelProperty(value = "Original issue date of document", example = "2022-01-01")
  @NotNull
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDate originalDocumentDate;
  @ApiModelProperty(value = "Issuer of document (partner name)", example = "MOL")
  @NotBlank(message = "PartnerName is required.")
  private String partnerName;
  @ApiModelProperty(value = "Enabled status", example = "true")
  private Boolean enabled;

}
