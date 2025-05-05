package com.main.numberManager.dtos.Portability;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPortabilityDTO{
         String razao;
         String documento;
         List<String> numeros;
         MultipartFile fatura;
}
