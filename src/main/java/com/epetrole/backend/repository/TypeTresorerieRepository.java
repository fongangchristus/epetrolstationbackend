package com.epetrole.backend.repository;

import com.epetrole.backend.domain.TypeTresorerie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeTresorerie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTresorerieRepository extends JpaRepository<TypeTresorerie, Long> {

}
