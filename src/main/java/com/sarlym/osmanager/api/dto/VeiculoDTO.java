package com.sarlym.osmanager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDTO {

    private String marca;
    private String modelo;
    private String motor;
    private String placa;
    private ClienteDTO cliente;

}