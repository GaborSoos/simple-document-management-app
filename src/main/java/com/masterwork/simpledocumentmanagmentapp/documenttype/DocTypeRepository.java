package com.masterwork.simpledocumentmanagmentapp.documenttype;

import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DocTypeRepository extends CrudRepository<DocumentType, Long> {

  List<DocumentType> findAll();

  Optional<DocumentType> findByName(String docTypeName);

  @Transactional
  void deleteByName(String docTypeName);

  Boolean existsDocumentTypeByName(String newDocTypeName);

}
