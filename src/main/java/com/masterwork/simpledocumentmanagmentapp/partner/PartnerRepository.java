package com.masterwork.simpledocumentmanagmentapp.partner;

import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends CrudRepository<Partner, Long> {

  List<Partner> findAll();

  Optional<Partner> findByName(String partnerName);

  @Transactional
  void deleteByName(String partnerName);

  boolean existsPartnerByName(String partnerName);
}
