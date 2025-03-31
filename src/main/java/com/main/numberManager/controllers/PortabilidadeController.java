package com.main.numberManager.controllers;

import com.main.numberManager.dtos.portabilidade.RequestPortabilidadeDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.PortabilidadeModel;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.services.OperadorasService;
import com.main.numberManager.services.PortabilidadeService;
import com.main.numberManager.services.ProvedorService;
import com.main.numberManager.services.UsuarioService;
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
    private final OperadorasService operadorasService;//

    public PortabilidadeController(PortabilidadeService portabilidadeService, UsuarioService usuarioService, ProvedorService provedorService, OperadorasService operadorasService){
        this.portabilidadeService = portabilidadeService;
        this.usuarioService = usuarioService;
        this.provedorService = provedorService;
        this.operadorasService = operadorasService;
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

        Integer codigoCnl = operadorasService.findByCodigoCnl(
                        portabilidadeDTO.prefixo(),
                        portabilidadeDTO.mcdu(),
                        portabilidadeDTO.codigoNacional()
                )
                .orElseThrow(() -> new NotFoundException("Numero não alocado"));

        portabilidadeModel.setCnl(codigoCnl);

        return ResponseEntity.status(HttpStatus.OK).body(portabilidadeService.save(portabilidadeModel));
    }
}
