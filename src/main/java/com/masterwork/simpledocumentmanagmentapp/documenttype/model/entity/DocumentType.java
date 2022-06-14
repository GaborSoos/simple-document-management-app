package com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity;

import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "doc_types")
public class DocumentType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @OneToMany(mappedBy = "documentType")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Document> documents = new ArrayList<>();

  public DocumentType(String name) {
    this.name = name;
  }
}
