package com.sarlym.gearflowapi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.sarlym.gearflowapi.api.dto.mapper.VeiculoMapper;
import com.sarlym.gearflowapi.api.dto.request.VeiculoRequest;
import com.sarlym.gearflowapi.api.dto.response.VeiculoDTO;
import com.sarlym.gearflowapi.domain.service.VeiculoService;

import java.util.List;

@RestController
@RequestMapping(value = "api/veiculos", produces = "application/json")
@Tag(name = "Veiculos", description = "Operações relacionadas aos veículos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final VeiculoMapper veiculoMapper;

    public VeiculoController(VeiculoService veiculoService, VeiculoMapper veiculoMapper) {
        this.veiculoService = veiculoService;
        this.veiculoMapper = veiculoMapper;
    }

    @Operation(summary = "Lista todos os veiculos", description = "Faz a listagem de todos os veiuclos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os veiculos"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar veiculos")
    })
    @GetMapping
    public List<VeiculoDTO> listarVeiculos() {
        return veiculoMapper.modelListaParaDTOLista(veiculoService.listarVeiculos());
    }

    @Operation(summary = "Realiza busca de veiculos por ID", description = "Busca um veiculo no sistema utilizando o ID como parametro.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veiculo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de cliente"),
    })
    @GetMapping("/{id}")
    public VeiculoDTO buscarVeiculo(
            @Parameter(name = "id", description = "ID único do veiculo", required = true, example = "1") @PathVariable(name = "id") Long id) {
        return veiculoMapper.modeloParaDTO(veiculoService.buscarVeiculoOuErro(id));
    }

    @Operation(summary = "Cadastra ordem de veiculo", description = "Cadastra uma nova ordem de serviço ao passar os valores placa, marca, modelo, ano, cor e quilometragem veiculo", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veiculo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar pesquisa")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoDTO cadastrarVeiculo(@RequestBody(required = true) VeiculoRequest veiculoRequest) {
        return veiculoMapper.modeloParaDTO(veiculoService.cadastrarVeiculo(veiculoRequest));
    }

    @Operation(summary = "Alterar o veiculo", description = "Altera um registro de um veiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID de veiculo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização")
    })
    @PutMapping("/{id}")
    public VeiculoDTO alterarVeiculo(
            @Parameter(name = "id", description = "ID único do veiculo", required = true, example = "1") @PathVariable(name = "id") Long id,
            @RequestBody(required = true) VeiculoRequest veiculoRequest) {
        return veiculoMapper.modeloParaDTO(veiculoService.alterarVeiculo(id, veiculoRequest));
    }

}
