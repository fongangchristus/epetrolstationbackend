package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Carburant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Carburant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarburantRepository extends JpaRepository<Carburant, Long> {

}
