package com.main.numberManager.domain.baseDids;

import com.main.numberManager.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidsRepository extends JpaRepository<DidsModel,Integer> {

    Page<DidsModel> findByProvedor(ProviderModel provedor, Pageable pageable);

}
