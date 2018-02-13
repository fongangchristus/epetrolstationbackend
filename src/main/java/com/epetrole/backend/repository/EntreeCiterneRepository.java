package com.epetrole.backend.repository;

import com.epetrole.backend.domain.EntreeCiterne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EntreeCiterne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntreeCiterneRepository extends JpaRepository<EntreeCiterne, Long> {

}
