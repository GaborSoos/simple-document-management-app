package com.masterwork.simpledocumentmanagmentapp.security.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Boolean enabled;
  @OneToMany(mappedBy = "role")
  private List<UserRole> userRoles;

  public Role(String name, Boolean enabled) {
    this.name = name;
    this.enabled = enabled;
  }

}
