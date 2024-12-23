package com.sarlym.osmanager.domain.service;

import com.sarlym.osmanager.domain.model.Veiculo;
import com.sarlym.osmanager.domain.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo buscaVeiculo(String placa) {
        return veiculoRepository.findVeiculoByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com a placa: " + placa));
    }

}
