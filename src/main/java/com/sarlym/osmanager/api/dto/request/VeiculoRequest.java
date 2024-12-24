package com.sarlym.osmanager.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoRequest {

    private String marca;
    private String modelo;
    private String motor;
    private String placa;
    private ClienteIdRequest cliente;

}
