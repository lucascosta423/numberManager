package com.main.numberManager.controllers.portabilidade;

import com.main.numberManager.dtos.portabilidade.RequestPortabilidadeDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.cnl.CnlGeralModel;
import com.main.numberManager.models.portabilidade.PortabilidadeModel;
import com.main.numberManager.models.provedor.ProvedorModel;
import com.main.numberManager.models.usuario.UsuarioModel;
import com.main.numberManager.repositorys.cnl.CnlGeralRepository;
import com.main.numberManager.services.portabilidade.PortabilidadeService;
import com.main.numberManager.services.provedor.ProvedorService;
import com.main.numberManager.services.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portabilidade")
public class PortabilidadeController {
    private final PortabilidadeService portabilidadeService;
    private final UsuarioService usuarioService;
    private final ProvedorService provedorService;
    private final CnlGeralRepository cnlGeralRepository;//

    public PortabilidadeController(PortabilidadeService portabilidadeService, UsuarioService usuarioService, ProvedorService provedorService, CnlGeralRepository cnlGeralRepository){
        this.portabilidadeService = portabilidadeService;
        this.usuarioService = usuarioService;
        this.provedorService = provedorService;
        this.cnlGeralRepository = cnlGeralRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<PortabilidadeModel> save(@RequestBody @Valid RequestPortabilidadeDTO portabilidadeDTO){
        var portabilidadeModel = new PortabilidadeModel();
        BeanUtils.copyProperties(portabilidadeDTO,portabilidadeModel);

        UsuarioModel usuarioModel = usuarioService.getById(portabilidadeDTO.usuario())
                .orElseThrow(() -> new NotFoundException("Usuario Não Encontrado"));
        portabilidadeModel.setUsuario(usuarioModel.getUsuario());

        ProvedorModel provedorModel = provedorService.findById(portabilidadeDTO.provedor())
                .orElseThrow(() -> new NotFoundException("Provedor Não Encontrado"));
        portabilidadeModel.setProvedor(provedorModel);

        String doisPrimeiros = portabilidadeDTO.numero().substring(0, 2);
        String quatroMeio = portabilidadeDTO.numero().substring(2, 6);
        String quatroUltimos = portabilidadeDTO.numero().substring(6);

        System.out.println();

        CnlGeralModel cnlGeralModel = cnlGeralRepository.findNumero(Integer.parseInt(quatroMeio),Integer.parseInt(quatroUltimos),Integer.parseInt(doisPrimeiros));

        System.out.println(cnlGeralModel.getCodigoCnl());

        portabilidadeModel.setCodigoCnl(cnlGeralModel.getCodigoCnl());

        return ResponseEntity.status(HttpStatus.OK).body(portabilidadeService.save(portabilidadeModel));
    }
}
