package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Reservoir;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reservoir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, Long> {

}
