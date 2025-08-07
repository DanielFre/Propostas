package com.danielfrey.propostas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<h1>Bem-vindo ao sistema de Propostas de Crédito</h1>" +
               "<p>Use os endpoints da API para criar, listar e gerenciar propostas.</p>";
    }
}
