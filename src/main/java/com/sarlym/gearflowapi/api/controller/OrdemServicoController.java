package com.sarlym.gearflowapi.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sarlym.gearflowapi.api.core.enums.Status;
import com.sarlym.gearflowapi.api.dto.mapper.OrdemServicoMapper;
import com.sarlym.gearflowapi.api.dto.request.OrdemServicoRequest;
import com.sarlym.gearflowapi.api.dto.request.PecaOrdemServicoRequest;
import com.sarlym.gearflowapi.api.dto.request.ServicoPrestadoRequest;
import com.sarlym.gearflowapi.api.dto.response.OrdemServicoDTO;
import com.sarlym.gearflowapi.api.dto.response.OrdemServicoResumo;
import com.sarlym.gearflowapi.domain.model.OrdemServico;
import com.sarlym.gearflowapi.domain.service.OrdemServicoService;
import com.sarlym.gearflowapi.domain.service.PdfService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "api/ordens-servico", produces = { "application/json" })
@Tag(name = "Ordens-Servico", description = "Operações com as ordens de serviço")
public class OrdemServicoController {

    private OrdemServicoService ordemServicoService;
    private PdfService pdfService;
    private OrdemServicoMapper ordemServicoMapper;

    public OrdemServicoController(OrdemServicoService ordemServicoService, OrdemServicoMapper ordemServicoMapper,
            PdfService pdfService) {
        this.ordemServicoService = ordemServicoService;
        this.ordemServicoMapper = ordemServicoMapper;
        this.pdfService = pdfService;
    }

    @Operation(summary = "Busca por ordem de serviço", description = "Busca uma OS no sistema utilizando o ID como parametro.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mecanico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de Ordem de Serviço"),
    })
    @GetMapping("/{id}")
    public OrdemServicoDTO buscamosOrdemServico(
            @Parameter(name = "id", description = "ID único da ordem de serviço", required = true, example = "1") @PathVariable(value = "id") Long id) {
        return ordemServicoMapper.modeloParaDTO(ordemServicoService.buscaOrdemServicoOuErro(id));
    }

    @Operation(summary = "Busca por ordem de serviço das OS ativos", description = "Busca uma OS no sistema retornando apenas os ativos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mecanico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de Ordem de Serviço"),
    })
    @GetMapping("/lista/ativos")
    public List<OrdemServicoResumo> buscaFiltrada() {
        return ordemServicoMapper.modeloListaParaListaDTOResumo(ordemServicoService.buscaListaAtivos());
    }

    @Operation(summary = "Busca por ordem de serviço", description = "Busca uma OS no sistema utilizando vários parametros.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mecanico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de Ordem de Serviço"),
    })
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrdemServicoResumo> buscaFiltrada(
            @RequestParam(required = false) String numeroOs,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long veiculoId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime dataFim) {
        List<OrdemServico> ordens = ordemServicoService.buscaComFiltros(numeroOs, status, veiculoId, dataInicio,
                dataFim);
        return ordemServicoMapper.modeloListaParaListaDTOResumo(ordens);
    }

    @Operation(summary = "Cadastra ordem de serviço", description = "Cadastra uma nova ordem de serviço ao passar os valores veiculoID, mecanicoID e descrição do problema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar pesquisa")
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrdemServicoDTO criaOrdemDeServico(@RequestBody(required = true) OrdemServicoRequest ordemServicoRequest) {
        return ordemServicoMapper.modeloParaDTO(ordemServicoService.salvar(ordemServicoRequest));
    }

    @Operation(summary = "Alterar o serviço", description = "Altera um registro de uma ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço, mecanico ou veiculo não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar atualização")
    })
    @PutMapping("/{id}")
    public OrdemServicoDTO alteraOrdemServico(
            @Parameter(name = "id", description = "ID único da ordem de serviço", required = true, example = "1") @PathVariable(name = "id") Long id,
            @RequestBody(required = true) OrdemServicoRequest ordemServicoRequest) {
        return ordemServicoMapper.modeloParaDTO(ordemServicoService.alterarOrdemServico(id, ordemServicoRequest));
    }

    @Operation(summary = "Deletar o serviço", description = "Deleta um registro de uma ordem de serviço", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID de ordem de serviço não encontrado"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaOrdemServico(
            @Parameter(name = "id", description = "ID único da OS", required = true, example = "1") @PathVariable(name = "id") Long id) {
        ordemServicoService.deletaOrdemServico(id);
    }

    @Operation(summary = "Associa um serviço prestado", description = "Associa um serviço prestado a uma ordem de serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço, mecanico ou veiculo não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar associação")
    })
    @PostMapping("/{id}/servico")
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoDTO associarServico(
            @Parameter(name = "id", description = "ID único da OS", required = true, example = "1") @PathVariable(name = "id") Long id,
            @RequestBody ServicoPrestadoRequest request) {
        return ordemServicoMapper.modeloParaDTO(ordemServicoService.adicionaServico(id, request));
    }

    @Operation(summary = "Associa uma peça", description = "Associa uma peça a uma ordem de serviço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço, mecanico ou veiculo não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar associação")
    })
    @PostMapping("/{id}/peca")
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoDTO associarPeca(
    @Parameter(name = "id", description = "ID único da OS", required = true, example = "1")    
    @PathVariable(name = "id") Long id,
            @RequestBody PecaOrdemServicoRequest request) {
        return ordemServicoMapper.modeloParaDTO(ordemServicoService.adicionaProduto(id, request));
    }

    @Operation(summary = "Cria PDF da ordem de serviço selecionada", description = "Busca uma OS no sistema utilizando o ID como parametro e gera um PDF.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mecanico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de Ordem de Serviço"),
    })
    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] gerarPdf(
    @Parameter(name = "id", description = "ID único da ordem de serviço", required = true, example = "1")    
    @PathVariable(value = "id") Long id) {
        OrdemServico ordem = ordemServicoService.buscaOrdemServicoOuErro(id);
        return pdfService.gerarPdf(ordem);
    }

    @Operation(summary = "Cria HTML da ordem de serviço selecionada", description = "Busca uma OS no sistema utilizando o ID como parametro e gera um HTML.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mecanico não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de Ordem de Serviço"),
    })
    @GetMapping(value = "/{id}/html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String gerarHtml(
    @Parameter(name = "id", description = "ID único da ordem de serviço", required = true, example = "1")    
        @PathVariable(value = "id") Long id) {
        OrdemServico ordem = ordemServicoService.buscaOrdemServicoOuErro(id);
        return pdfService.gerarHtml(ordem);
    }

}
