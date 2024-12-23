package com.sarlym.osmanager.api.controller;

import com.sarlym.osmanager.api.dto.ClienteDTO;
import com.sarlym.osmanager.api.dto.dtoconverter.ClienteConverter;
import com.sarlym.osmanager.api.dto.request.ClienteRequest;
import com.sarlym.osmanager.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cliente", produces = {"application/json"})
@Tag(name = "Cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteConverter clienteConverter;

    @Operation(summary = "Realiza busca de cliente por ID", description = "Busca um cliente no sistema utilizando o ID como parametro.",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de cliente"),
    })
    @GetMapping("/{id}")
    public ClienteDTO buscarCliente(
            @Parameter(name = "id", description = "ID único do cliente", required = true, example = "1"
            )@PathVariable Long id){
        return clienteConverter.paraDTO(clienteService.buscarClienteOuErro(id));
    }

    @Operation(summary = "Realiza listagem de clientes", description = "Lista os clientes cadastrados no sistema, mostrando o nome, e-mail e telefone.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca de clientes"),
    })
    @GetMapping
    public List<ClienteDTO> listarClientes(){
        return clienteConverter.paraDTOLista(clienteService.clientes());
    }

    @Operation(summary = "Realiza cadastro de cliente", description = "Cadastra no sistema uma pessoa ao passar o nome, e-mail e telefone.",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a cadastro de cliente, e-mail já cadastrado."),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO cadastrarCliente(@RequestBody ClienteRequest clienteRequest){
        return clienteConverter.paraDTO(clienteService.cadastrarCliente(clienteRequest));
    }

    @Operation(summary = "Alteração de cliente", description = "Altera uma informação do cliente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID de cliente não encontrado"),
    })
    @PutMapping("/{id}")
    public ClienteDTO alterarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest){
        return clienteConverter.paraDTO(clienteService.alterarCliente(id,clienteRequest));
    }

    @Operation(summary = "Deletar de cliente", description = "Deleta um registro de cliente", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID de cliente não encontrado"),
    })
    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
    }

}
