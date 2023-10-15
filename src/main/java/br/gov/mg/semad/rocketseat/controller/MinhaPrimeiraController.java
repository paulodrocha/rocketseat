package br.gov.mg.semad.rocketseat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
// http://localhost:8080/primeiraRota/primeiroMetodo

public class MinhaPrimeiraController {

    @GetMapping("/primeiroMetodo")

    public String primeiraMensagem(){
        return "Funcionou";
    }
    
}
