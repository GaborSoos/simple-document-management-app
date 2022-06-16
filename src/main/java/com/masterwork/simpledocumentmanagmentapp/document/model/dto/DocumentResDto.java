package com.masterwork.simpledocumentmanagmentapp.document.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "DocumentTag model information for client.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DocumentResDto {

  @ApiModelProperty(value = "application generated id", example = "1")
  private Long id;
  @ApiModelProperty(value = "Original id of document", example = "INV/2022/000001")
  private String originalDocumentId;
  @ApiModelProperty(value = "DocumentType name", example = "invoice")
  private String documentTypeName;
  @ApiModelProperty(value = "Subject of document", example = "Fuel invoice - 2022.01.")
  private String subject;
  @ApiModelProperty(value = "Description of document", example = "This is a simple fuel invoice of a company car.")
  private String description;
  @ApiModelProperty(value = "Original issue date of document", example = "2022-01-01")
  private LocalDate originalDocumentDate;
  @ApiModelProperty(value = "Issuer of document (partner name)", example = "MOL")
  private String partnerName;
  @ApiModelProperty(value = "DocumentTag list")
  private List<String> docTags;
  @ApiModelProperty(value = "Enabled status", example = "true")
  private Boolean enabled;



}
