package com.masterwork.simpledocumentmanagmentapp.document.model.entity;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "documents")
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String originalDocumentId;
  @ManyToOne
  @JoinColumn(name = "doc_type_id", referencedColumnName = "id")
  private DocumentType documentType;
  private String subject;
  private String description;
  private LocalDate originalDocumentDate;
  @ManyToOne
  @JoinColumn(name = "partner_id", referencedColumnName = "id")
  private Partner partner;
  private Boolean enabled;
  @ManyToMany
  @JoinTable(
      name = "document_doc_tags",
      joinColumns = @JoinColumn(name = "document_id"),
      inverseJoinColumns = @JoinColumn(name = "doc_tag_id"))
  private List<DocumentTag> documentTags = new ArrayList<>();

  public Document(String originalDocumentId, DocumentType documentType, String subject, String description,
                  LocalDate originalDocumentDate, Partner partner, Boolean enabled) {
    this.originalDocumentId = originalDocumentId;
    this.documentType = documentType;
    this.subject = subject;
    this.description = description;
    this.originalDocumentDate = originalDocumentDate;
    this.partner = partner;
    this.enabled = enabled;
  }

}
