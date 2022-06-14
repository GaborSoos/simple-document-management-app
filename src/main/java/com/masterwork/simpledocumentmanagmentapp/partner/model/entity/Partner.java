package com.masterwork.simpledocumentmanagmentapp.partner.model.entity;

import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "partners")
public class Partner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String address;
  private String email;
  private String telephone;
  private Boolean enabled = false;
  @OneToMany(mappedBy = "partner")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Document> documents = new ArrayList<>();

  public Partner(String name, String address, String email, String telephone, Boolean enabled) {
    this.name = name;
    this.address = address;
    this.email = email;
    this.telephone = telephone;
    this.enabled = enabled;
  }

}
