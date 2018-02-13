package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Intervenant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Intervenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervenantRepository extends JpaRepository<Intervenant, Long> {

}
