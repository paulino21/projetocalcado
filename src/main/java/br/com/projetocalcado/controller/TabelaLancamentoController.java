package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.financeiro.tipoLancamento.DadosDetalheTabelaLancamento;
import br.com.projetocalcado.domain.financeiro.tipoLancamento.DadosTabelaLancamento;
import br.com.projetocalcado.domain.financeiro.tipoLancamento.TabelaLancamento;
import br.com.projetocalcado.domain.financeiro.tipoLancamento.TabelaLancamentoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@RequestMapping("/tabelalancamentos")
@RestController
public class TabelaLancamentoController {
    @Autowired
    TabelaLancamentoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraTabelaLancamento(@RequestBody @Valid DadosTabelaLancamento dadosTabelaLancamento, UriComponentsBuilder uriBuilder){
        var tabelaLancamento = new TabelaLancamento(dadosTabelaLancamento);
        repository.save(tabelaLancamento);
        var uri = uriBuilder.path("categoria/{id}").buildAndExpand(tabelaLancamento.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheTabelaLancamento(tabelaLancamento));
    }
    @GetMapping
    public ResponseEntity listaTabelaLancamento(){
        List<TabelaLancamento> tabelaLancamentos = repository.findAll();
        return ResponseEntity.ok(tabelaLancamentos);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaTabelaLancamento(@PathVariable Long id){
        var tabelaLancamento = repository.getReferenceById(id);
        repository.delete(tabelaLancamento);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalheTabelaLancamento(@PathVariable Long id){
        var tabelaLancamento = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheTabelaLancamento(tabelaLancamento));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizaTabelaLancamento(@RequestBody @Valid DadosDetalheTabelaLancamento dadosDetalheTabelaLancamento){
        var tabelaLancamento = repository.getReferenceById(dadosDetalheTabelaLancamento.id());
        tabelaLancamento.atualizaCategoria(dadosDetalheTabelaLancamento);
        return ResponseEntity.ok(new DadosDetalheTabelaLancamento(tabelaLancamento));
    }
}
