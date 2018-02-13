package com.epetrole.backend.repository;

import com.epetrole.backend.domain.EntreeProduit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EntreeProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntreeProduitRepository extends JpaRepository<EntreeProduit, Long> {

}
