package com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity;

import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "doc_tags")
public class DocumentTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(mappedBy = "documentTags")
  private List<Document> documents = new ArrayList<>();

  public DocumentTag(String name) {
    this.name = name;
  }


}
