package com.epetrole.backend.repository;

import com.epetrole.backend.domain.ModeReglement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ModeReglement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeReglementRepository extends JpaRepository<ModeReglement, Long> {

}
