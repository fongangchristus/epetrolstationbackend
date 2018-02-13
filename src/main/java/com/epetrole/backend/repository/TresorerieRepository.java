package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Tresorerie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tresorerie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TresorerieRepository extends JpaRepository<Tresorerie, Long> {

}
