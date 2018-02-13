package com.epetrole.backend.repository;

import com.epetrole.backend.domain.TypeIntervenant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeIntervenant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeIntervenantRepository extends JpaRepository<TypeIntervenant, Long> {

}
