package com.sarlym.osmanager.api.dto.response;

import com.sarlym.osmanager.api.dto.request.VeiculoRequest;
import com.sarlym.osmanager.domain.model.Cliente;
import com.sarlym.osmanager.domain.model.Veiculo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VeiculoResponse {

    @Autowired
    private ModelMapper modelMapper;

    public Veiculo paraModel (VeiculoRequest veiculoRequest){
        return modelMapper.map(veiculoRequest, Veiculo.class);
    }

    public void copiaDTOparaModel(VeiculoRequest veiculoRequest, Veiculo veiculo){
        veiculo.setCliente(new Cliente());
        modelMapper.map(veiculoRequest, veiculo);
    }
}
