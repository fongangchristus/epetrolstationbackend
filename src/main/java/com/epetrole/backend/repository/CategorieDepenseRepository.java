package com.epetrole.backend.repository;

import com.epetrole.backend.domain.CategorieDepense;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CategorieDepense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieDepenseRepository extends JpaRepository<CategorieDepense, Long> {

}
