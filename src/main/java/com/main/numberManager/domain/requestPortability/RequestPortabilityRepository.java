package com.main.numberManager.domain.requestPortability;

import com.main.numberManager.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestPortabilityRepository extends JpaRepository<RequestPortabilityModel, String> {
    Page<RequestPortabilityModel> findByProvedor(ProviderModel provedor, Pageable pageable);
}
