package com.main.numberManager.repositorys;

import com.main.numberManager.models.ProviderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<ProviderModel,Integer> {

}
