package com.masterwork.simpledocumentmanagmentapp.security.repository;

import com.masterwork.simpledocumentmanagmentapp.security.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findAll();

  Optional<User> findByUserName(String userName);

  @Transactional
  void deleteByUserName(String userName);

}
