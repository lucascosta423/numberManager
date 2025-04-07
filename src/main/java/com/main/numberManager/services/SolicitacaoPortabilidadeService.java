package com.main.numberManager.services;


import com.main.numberManager.dtos.portabilidade.SolicitacaoPortabilidadeDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.models.SolicitacaoPortabilidadeModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.SolicitacaoPortabilidadeRepository;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoPortabilidadeService {

    private final SolicitacaoPortabilidadeRepository solicitacaoPortabilidadeRepository;
    private final UsuarioService usuarioService;
    private final ProvedorService provedorService;
    private final SolicitacaoNumeroService solicitacaoNumeroService;

    public SolicitacaoPortabilidadeService(SolicitacaoPortabilidadeRepository solicitacaoPortabilidadeRepository, UsuarioService usuarioService, ProvedorService provedorService, SolicitacaoNumeroService solicitacaoNumeroService) {
        this.solicitacaoPortabilidadeRepository = solicitacaoPortabilidadeRepository;
        this.usuarioService = usuarioService;
        this.provedorService = provedorService;
        this.solicitacaoNumeroService = solicitacaoNumeroService;
    }

    public SucessResponse save(SolicitacaoPortabilidadeDTO portabilidadeDTO) {
        var portabilidadeModel = new SolicitacaoPortabilidadeModel();
        BeanUtils.copyProperties(portabilidadeDTO,portabilidadeModel);

        UsuarioModel usuarioModel = usuarioService.getByIdUser(portabilidadeDTO.usuario())
                .orElseThrow(() -> new NotFoundException("Usuario Não Encontrado"));
        portabilidadeModel.setUsuario(usuarioModel);

        ProvedorModel provedorModel = provedorService.findById(portabilidadeDTO.provedor())
                .orElseThrow(() -> new NotFoundException("Provedor Não Encontrado"));
        portabilidadeModel.setProvedor(provedorModel);

        var portabilidadeSalva = solicitacaoPortabilidadeRepository.save(portabilidadeModel);

        solicitacaoNumeroService.criarNumerosPortabilidade(portabilidadeSalva,portabilidadeDTO.numeros());

        return new SucessResponse("Solicitaçao criada com sucesso","OK");
    }

    public Page<SolicitacaoPortabilidadeModel> findAll(Pageable pageable) {
        return solicitacaoPortabilidadeRepository.findAll(pageable);
    }

}
