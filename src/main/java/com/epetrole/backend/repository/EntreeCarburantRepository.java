package com.epetrole.backend.repository;

import com.epetrole.backend.domain.EntreeCarburant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EntreeCarburant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntreeCarburantRepository extends JpaRepository<EntreeCarburant, Long> {

}
