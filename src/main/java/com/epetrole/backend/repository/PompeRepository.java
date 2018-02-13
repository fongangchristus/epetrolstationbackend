package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Pompe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pompe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PompeRepository extends JpaRepository<Pompe, Long> {

}
