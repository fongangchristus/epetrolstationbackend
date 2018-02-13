package com.epetrole.backend.repository;

import com.epetrole.backend.domain.SortieCarburant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SortieCarburant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SortieCarburantRepository extends JpaRepository<SortieCarburant, Long> {

}
