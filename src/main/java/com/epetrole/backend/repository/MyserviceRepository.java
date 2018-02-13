package com.epetrole.backend.repository;

import com.epetrole.backend.domain.Myservice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Myservice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyserviceRepository extends JpaRepository<Myservice, Long> {

}
