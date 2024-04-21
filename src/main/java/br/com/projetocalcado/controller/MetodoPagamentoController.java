package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.metodoPagamento.DadosDetalheFormaPagamento;
import br.com.projetocalcado.domain.metodoPagamento.DadosFormaPgto;
import br.com.projetocalcado.domain.metodoPagamento.FormaPagamento;
import br.com.projetocalcado.domain.metodoPagamento.FormaPagamentoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/formaPagamentos")
public class MetodoPagamentoController {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraFormaPagamento(@RequestBody @Valid DadosFormaPgto dadosFormaPgto, UriComponentsBuilder uriBuilder) {
        var formaPagto = new FormaPagamento(dadosFormaPgto);
        formaPagamentoRepository.save(formaPagto);
        var uri = uriBuilder.path("formaPagto/{id}").buildAndExpand(formaPagto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalheFormaPagamento(formaPagto));
    }

    @PutMapping
    @Transactional
    public ResponseEntity alteraFormaPagamento(@RequestBody @Valid DadosDetalheFormaPagamento dadosDetalheFormaPagamento) {
        var formapgto = formaPagamentoRepository.getReferenceById(dadosDetalheFormaPagamento.id());
        formapgto.atualuzaFormaPagamento(dadosDetalheFormaPagamento);
        return ResponseEntity.ok(new DadosDetalheFormaPagamento(formapgto));
    }

    @GetMapping
    public ResponseEntity metodoPagamento() {
        List<FormaPagamento> formasPagamentos = formaPagamentoRepository.findAll();
        return ResponseEntity.ok(formasPagamentos);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaFormaPagamento(@PathVariable Long id) {
       var formaPagto = formaPagamentoRepository.getReferenceById(id);
       formaPagamentoRepository.delete(formaPagto);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalheFormaPagamento(@PathVariable Long id) {
       var formaPagto = formaPagamentoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalheFormaPagamento(formaPagto));
    }
}

