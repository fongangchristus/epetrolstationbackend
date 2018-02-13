package com.epetrole.backend.repository;

import com.epetrole.backend.domain.FraisRecue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FraisRecue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraisRecueRepository extends JpaRepository<FraisRecue, Long> {

}
