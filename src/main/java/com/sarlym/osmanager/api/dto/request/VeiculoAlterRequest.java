package com.sarlym.osmanager.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VeiculoAlterRequest {
    private String marca;
    private String modelo;
    private String motor;
    private String placa;
}
