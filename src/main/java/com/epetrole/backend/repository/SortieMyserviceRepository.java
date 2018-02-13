package com.epetrole.backend.repository;

import com.epetrole.backend.domain.SortieMyservice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SortieMyservice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SortieMyserviceRepository extends JpaRepository<SortieMyservice, Long> {

}
