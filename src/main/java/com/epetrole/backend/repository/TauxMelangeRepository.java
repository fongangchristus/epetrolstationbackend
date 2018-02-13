package com.epetrole.backend.repository;

import com.epetrole.backend.domain.TauxMelange;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TauxMelange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TauxMelangeRepository extends JpaRepository<TauxMelange, Long> {

}
