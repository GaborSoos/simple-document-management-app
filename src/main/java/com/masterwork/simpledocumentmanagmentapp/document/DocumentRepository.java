package com.masterwork.simpledocumentmanagmentapp.document;

import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {

  @Query("SELECT d FROM documents d LEFT JOIN d.documentTags documentTags WHERE"
      + " (:partnerName is null or d.partner.name =:partnerName) AND"
      + " (:docTypeName is null or d.documentType.name =:docTypeName ) AND"
      + " (:docTagName is null or documentTags.name =:docTagName ) AND"
      + " (:from is null or d.originalDocumentDate <=:from ) AND"
      + " (:to is null or d.originalDocumentDate <=:to )")
  List<Document> findAllByParam(@Param("partnerName") String partnerName,@Param("docTypeName") String docTypeName,
                                @Param("docTagName") String docTagName, @Param("from") LocalDate from,
                                @Param("to") LocalDate to);

  void deleteById(Long id);

  boolean existsDocumentByOriginalDocumentIdAndPartner(String origDocId, Partner partner);

}
