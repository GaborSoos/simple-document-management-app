package com.masterwork.simpledocumentmanagmentapp.security.repository;

import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

  Optional<Role> findByName(String roleName);

  List<Role> findAll();

  @Transactional
  void deleteByName(String roleName);

}
