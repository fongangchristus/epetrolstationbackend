package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Citerne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Citerne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiterneRepository extends JpaRepository<Citerne, Long> {

}
