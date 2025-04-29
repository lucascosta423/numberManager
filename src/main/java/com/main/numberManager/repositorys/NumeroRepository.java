package com.main.numberManager.repositorys;

import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.models.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumeroRepository extends JpaRepository<NumeroModel,Integer> {

    Page<NumeroModel> findByProvedor(ProviderModel provedor, Pageable pageable);

}
