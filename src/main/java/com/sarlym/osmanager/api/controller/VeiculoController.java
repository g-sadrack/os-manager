package com.sarlym.osmanager.api.controller;

import com.sarlym.osmanager.api.dto.VeiculoDTO;
import com.sarlym.osmanager.api.dto.dtoconverter.VeiculoConverter;
import com.sarlym.osmanager.domain.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private VeiculoConverter converter;

    @GetMapping("/{placa}")
    public VeiculoDTO buscaVeiculo(@PathVariable String placa){
        return converter.paraDTO(veiculoService.buscaVeiculo(placa));
    }

}
