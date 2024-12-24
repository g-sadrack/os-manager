package com.sarlym.osmanager.domain.service;

import com.sarlym.osmanager.api.dto.request.VeiculoRequest;
import com.sarlym.osmanager.api.dto.response.VeiculoResponse;
import com.sarlym.osmanager.domain.model.Cliente;
import com.sarlym.osmanager.domain.model.Veiculo;
import com.sarlym.osmanager.domain.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoResponse response;
    private final VeiculoRepository veiculoRepository;
    private final ClienteService clienteService;

    @Autowired
    public VeiculoService(VeiculoResponse response, VeiculoRepository veiculoRepository, ClienteService clienteService) {
        this.response = response;
        this.veiculoRepository = veiculoRepository;
        this.clienteService = clienteService;
    }

    public Veiculo buscaVeiculo(String placa) {
        return veiculoRepository.findVeiculoByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com a placa: " + placa));
    }

    public List<Veiculo> listaVeiculos() {
        return veiculoRepository.findAll();
    }

    @Transactional
    public Veiculo cadastrarVeiculo(VeiculoRequest veiculoRequest) {
        Veiculo veiculo = response.paraModel(veiculoRequest);
        Long clienteId = veiculo.getCliente().getId();
        Cliente cliente = clienteService.buscarClienteOuErro(clienteId);
        veiculo.setCliente(cliente);
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Veiculo alteraVeiculo(String placa, VeiculoRequest veiculoRequest) {
        Veiculo veiculo = buscaVeiculo(placa);
        response.copiaDTOparaModel(veiculoRequest, veiculo);
        Cliente cliente = clienteService.buscarClienteOuErro(veiculo.getId());
        veiculo.setCliente(cliente);

        return veiculoRepository.save(veiculo);
    }
}
