package com.sarlym.osmanager.api.dto.response;


import lombok.Data;

@Data
public class ServicoDTO {

    private Long id;
    private String codigo;
    private String descricao;
    private Integer tempo_medio;

}
