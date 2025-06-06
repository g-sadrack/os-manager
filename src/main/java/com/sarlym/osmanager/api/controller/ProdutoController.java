package com.sarlym.osmanager.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sarlym.osmanager.api.dto.mapper.ProdutoMapper;
import com.sarlym.osmanager.api.dto.request.ProdutoRequest;
import com.sarlym.osmanager.api.dto.response.ProdutoDTO;
import com.sarlym.osmanager.domain.service.ProdutoService;

@RestController
@RequestMapping(value = "api/produtos", produces = { "application/json" })
public class ProdutoController {

    private ProdutoService produtoService;
    private ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscaProduto(@PathVariable(name = "id") Long id) {
        return produtoMapper.modelParaDTO(produtoService.buscaProdutoOuErro(id));
    }

    @GetMapping
    public List<ProdutoDTO> listarProduto() {
        return produtoMapper.modeloParaDTOLista(produtoService.listaProdutos());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO cadastrarProduto(@RequestBody(required = true) ProdutoRequest produtoRequest) {
        return produtoMapper.modelParaDTO(produtoService.salvar(produtoRequest));
    }

    @PutMapping("/{id}")
    public ProdutoDTO alterarProduto(@PathVariable(name = "id") Long id,
            @RequestBody(required = true) ProdutoRequest produtoRequest) {
        return produtoMapper.modelParaDTO(produtoService.atualizarProduto(id, produtoRequest));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProduto(@PathVariable(name = "id") Long id) {
        produtoService.deletarProduto(id);
    }

}
