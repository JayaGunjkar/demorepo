package com.jsg.DocumentCreation.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsg.DocumentCreation.app.model.SanctionDetails;

@Repository
public interface SanctionDetailsRepository extends JpaRepository<SanctionDetails, Integer>{

}
