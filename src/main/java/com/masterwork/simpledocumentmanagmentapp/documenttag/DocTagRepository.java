package com.masterwork.simpledocumentmanagmentapp.documenttag;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DocTagRepository extends CrudRepository<DocumentTag, Long> {

  Optional<DocumentTag> findByName(String docTagName);

  List<DocumentTag> findAll();

  Boolean existsDocumentTagByName(String docTagName);

}
