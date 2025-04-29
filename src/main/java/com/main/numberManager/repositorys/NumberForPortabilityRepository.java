package com.main.numberManager.repositorys;

import com.main.numberManager.models.NumberForPortabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NumberForPortabilityRepository extends JpaRepository<NumberForPortabilityModel, String> {
}
