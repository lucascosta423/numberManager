package com.main.numberManager.domain.providers;

import com.main.numberManager.dtos.provider.RequestProviderDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.Enuns.Status;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public SucessResponse save(RequestProviderDTO dto) {

        var model = new ProviderModel();
        BeanUtils.copyProperties(dto, model);

        model.setStatus(Status.A);

        providerRepository.save(model);

        return new SucessResponse("Provedor Criado Com Sucesso", "OK");
    }

    public SucessResponse changeProviderStatus(Integer id){
        ProviderModel providerModel = findById(id);

        switch (providerModel.getStatus()){
            case A -> providerModel.setStatus(Status.I);
            case I -> providerModel.setStatus(Status.A);
        }

        providerRepository.save(providerModel);
        return new SucessResponse("Provedor Deletado ou Reativado com sucesso","OK");
    }

    public SucessResponse updateProvedor(Integer id, RequestProviderDTO dto){

        ProviderModel providerModel = findById(id);

        BeanUtils.copyProperties(dto, providerModel,"id");

        providerRepository.save(providerModel);

        return new SucessResponse("Provedor Atualizado Com Sucesso","OK");
    }

    public Page<ProviderModel> findAll(Pageable pageable) {
        return providerRepository.findAll(pageable);
    }

    public ProviderModel findById(Integer id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Provedor não encontrado"));

    }
}
