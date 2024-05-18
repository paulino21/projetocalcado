package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.financeiro.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@RequestMapping("/lancamentos")
@RestController
public class LacamentoController {
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    LancamentoService lancamentoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraLancamento(@RequestBody @Valid DadosLacamento dadosLacamento, UriComponentsBuilder uriBuilder){
        var lancamento = lancamentoService.cadastra(dadosLacamento);
        var uri = uriBuilder.path("lancamento/{id}").buildAndExpand(lancamento.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheLancamento(lancamento));
    }
    @GetMapping
    public ResponseEntity listaLancamento(){
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        return ResponseEntity.ok(lancamentos);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaLancamento(@PathVariable Long id){
        var lancamento = lancamentoRepository.getReferenceById(id);
        lancamentoRepository.delete(lancamento);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalheLancamento(@PathVariable Long id){
        var lancamento = lancamentoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheLancamento(lancamento));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizaLancamento(@RequestBody @Valid DadosDetalheLancamento dadosDetalheLancamento){
        var lancamento = lancamentoService.atualiza(dadosDetalheLancamento);
        return ResponseEntity.ok(new DadosDetalheLancamento(lancamento));
    }
}
