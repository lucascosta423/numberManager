package com.main.numberManager.services;

import com.main.numberManager.dtos.provedor.ProvedorDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.Enuns.Status;
import com.main.numberManager.repositorys.ProvedorRepository;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvedorService {

    private final ProvedorRepository provedorRepository;

    public ProvedorService(ProvedorRepository provedorRepository) {
        this.provedorRepository = provedorRepository;
    }

    public SucessResponse save(ProvedorDTO dto) {

        var model = new ProvedorModel();
        BeanUtils.copyProperties(dto, model);

        model.setStatus(Status.A);

        provedorRepository.save(model);

        return new SucessResponse("Provedor Criado Com Sucesso", "OK");
    }

    public SucessResponse changeProviderStatus(Integer id){
        ProvedorModel provedorModel = findById(id);

        switch (provedorModel.getStatus()){
            case A -> provedorModel.setStatus(Status.I);
            case I -> provedorModel.setStatus(Status.A);
        }

        provedorRepository.save(provedorModel);
        return new SucessResponse("Provedor Deletado ou Reativado com sucesso","OK");
    }

    public SucessResponse updateProvedor(Integer id, ProvedorDTO dto){

        ProvedorModel provedorModel = findById(id);

        BeanUtils.copyProperties(dto,provedorModel,"id");

        provedorRepository.save(provedorModel);

        return new SucessResponse("Provedor Atualizado Com Sucesso","OK");
    }

    public Page<ProvedorModel> findAll(Pageable pageable) {
        return provedorRepository.findAll(pageable);
    }

    public ProvedorModel findById(Integer id) {
        return provedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Provedor não encontrado"));

    }
}
