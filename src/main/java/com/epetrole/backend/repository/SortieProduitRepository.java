package com.epetrole.backend.repository;

import com.epetrole.backend.domain.SortieProduit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SortieProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SortieProduitRepository extends JpaRepository<SortieProduit, Long> {

}
