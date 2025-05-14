package com.main.numberManager.repositorys;

import com.main.numberManager.models.ProviderModel;
import com.main.numberManager.models.RequestPortabilityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestPortabilityRepository extends JpaRepository<RequestPortabilityModel, String> {
    Page<RequestPortabilityModel> findByProvedor(ProviderModel provedor, Pageable pageable);
}
