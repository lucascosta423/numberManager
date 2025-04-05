package com.main.numberManager.services;


import com.main.numberManager.dtos.portabilidade.RequestPortabilidadeDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumeroPortabilidadeModel;
import com.main.numberManager.models.PortabilidadeModel;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.PortabilidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortabilidadeService {

    private final PortabilidadeRepository portabilidadeRepository;
    private final UsuarioService usuarioService;
    private final ProvedorService provedorService;
    private final NumeroPortabilidadeService numeroPortabilidadeService;
    private final OperadorasService operadorasService;

    public PortabilidadeService(PortabilidadeRepository portabilidadeRepository, UsuarioService usuarioService, ProvedorService provedorService, NumeroPortabilidadeService numeroPortabilidadeService, OperadorasService operadorasService) {
        this.portabilidadeRepository = portabilidadeRepository;
        this.usuarioService = usuarioService;
        this.provedorService = provedorService;
        this.numeroPortabilidadeService = numeroPortabilidadeService;
        this.operadorasService = operadorasService;
    }

    public String save(RequestPortabilidadeDTO portabilidadeDTO) {
        var portabilidadeModel = new PortabilidadeModel();
        BeanUtils.copyProperties(portabilidadeDTO,portabilidadeModel);

        UsuarioModel usuarioModel = usuarioService.getById(portabilidadeDTO.usuario())
                .orElseThrow(() -> new NotFoundException("Usuario Não Encontrado"));
        portabilidadeModel.setUsuario(usuarioModel);

        ProvedorModel provedorModel = provedorService.findById(portabilidadeDTO.provedor())
                .orElseThrow(() -> new NotFoundException("Provedor Não Encontrado"));
        portabilidadeModel.setProvedor(provedorModel);

        PortabilidadeModel portabilidadeSalva = portabilidadeRepository.save(portabilidadeModel);

        List<NumeroPortabilidadeModel> listNumeros = addPortabilidaAndNumeroPortabilidade(portabilidadeSalva,portabilidadeDTO.numero());

        numeroPortabilidadeService.save(listNumeros);

        return "Portabilidade Criada";
    }

    public Page<PortabilidadeModel> findAll(Pageable pageable) {
        return portabilidadeRepository.findAll(pageable);
    }



    private List<NumeroPortabilidadeModel> addPortabilidaAndNumeroPortabilidade(PortabilidadeModel portabilidadeModel,List<String> numeros){

        var numeroPort = new NumeroPortabilidadeModel();
        var listNumerosPortabilidade = new ArrayList<NumeroPortabilidadeModel>();


        for(String numero : numeros){
            var codigoNacional = Integer.parseInt(numero.substring(0, 2));
            var prefixo = Integer.parseInt(numero.substring(2, 6));
            var mcdu = Integer.parseInt(numero.substring(6));

            System.out.println("++================================================");
            System.out.println(codigoNacional);
            System.out.println(prefixo);
            System.out.println(mcdu);
            System.out.println("++================================================");

            System.out.println(numeroPort.toString());

            var requestNumeroPortabilidade = operadorasService.findByNumeroPortabilidade(prefixo,mcdu,codigoNacional);
            BeanUtils.copyProperties(requestNumeroPortabilidade,numeroPort,"id","portabilidadeModel");



            numeroPort.setPortabilidadeModel(portabilidadeModel);
            listNumerosPortabilidade.add(numeroPort);


        }

        return listNumerosPortabilidade;
    }
}
