package com.sarlym.osmanager.api.controller;

import com.sarlym.osmanager.api.dto.VeiculoDTO;
import com.sarlym.osmanager.api.dto.dtoconverter.VeiculoConverter;
import com.sarlym.osmanager.api.dto.request.VeiculoRequest;
import com.sarlym.osmanager.domain.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private VeiculoConverter converter;

    @GetMapping("/{placa}")
    public VeiculoDTO buscaVeiculo(@PathVariable String placa) {
        return converter.paraDTO(veiculoService.buscaVeiculo(placa));
    }

    @GetMapping
    public List<VeiculoDTO> listaVeiculos(){
        return converter.paraDTOLista(veiculoService.listaVeiculos());
    }

    @PostMapping
    public VeiculoDTO cadastraVeiculo(@RequestBody VeiculoRequest veiculo){
        return converter.paraDTO(veiculoService.cadastrarVeiculo(veiculo));
    }

    @PutMapping("/{placa}")
    public VeiculoDTO alterarVeiculo(@PathVariable String placa, @RequestBody VeiculoRequest veiculo){
        return converter.paraDTO(veiculoService.alteraVeiculo(placa, veiculo));
    }

}
