package com.main.numberManager.domain.requestPortability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NumberForPortabilityRepository extends JpaRepository<NumberForPortabilityModel, String> {
}
