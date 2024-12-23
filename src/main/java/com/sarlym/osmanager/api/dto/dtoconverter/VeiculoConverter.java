package com.sarlym.osmanager.api.dto.dtoconverter;

import com.sarlym.osmanager.api.dto.VeiculoDTO;
import com.sarlym.osmanager.domain.model.Veiculo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VeiculoConverter {

    @Autowired
    private ModelMapper modelMapper;

    public VeiculoDTO paraDTO (Veiculo veiculo){
        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

    public List<VeiculoDTO> paraDTOLista (List<Veiculo> veiculos){
        return veiculos.stream().map(this::paraDTO).toList();
    }

}
