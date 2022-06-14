package com.masterwork.simpledocumentmanagmentapp.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_roles")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name="role_id")
  private Role role;
  private Boolean enabled;

}
