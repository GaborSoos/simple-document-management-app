package com.masterwork.simpledocumentmanagmentapp.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "username")
  private String userName;
  @Column(name = "firstname")
  private String firstName;
  @Column(name = "lastname")
  private String lastName;
  private String password;
  private Boolean enabled;
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<UserRole> userRoles = new ArrayList<>();

  public User(String userName, String firstName, String lastName, String password, Boolean enabled) {
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.enabled = enabled;
  }

}
