package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.nota.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/notas")
public class NotaController {
    @Autowired
    NotaService notaService;
    @Autowired
    NotaFiscalRepository notaFiscalRepository;

    @GetMapping("/{caminho}")
    public ResponseEntity pegaXmlNota(@PathVariable String caminho , UriComponentsBuilder uriBuilder){
            var infXml = notaService.devolveDadosXml(caminho);
            var nota = notaService.salvaNotaXml(infXml);
            var uri = uriBuilder.path("/nota/{id}").buildAndExpand(nota.id()).toUri();
        return ResponseEntity.created(uri).body(nota);
    }
    @GetMapping("/{ean}/{quantidade}")
    public ResponseEntity adicionaProduto(@PathVariable String ean, @PathVariable Integer quantidade) {
        return ResponseEntity.ok(notaService.adicionaProdutoNota(ean, quantidade));
    }
    @GetMapping("/itens")
    public ResponseEntity retornaItemNota(){
        return ResponseEntity.ok(notaService.retornaItensComQdeAlterada());
    }
    @PatchMapping("/alterarQuantidade/{id}/{acao}")
    public ResponseEntity adicionaQteProdutoPedido(@PathVariable Long id, @PathVariable String acao) {
        notaService.alteraQuantidadeItens(id , acao);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/alteraPrecoItens/{id}/{precoCusto}/{precoVenda}")
    public ResponseEntity alteraPreco(@PathVariable Long id, @PathVariable BigDecimal precoCusto, @PathVariable BigDecimal precoVenda) {
        notaService.alterarPrecoItens(id, precoCusto, precoVenda);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/removeProduto/{id}")
    public ResponseEntity removeProduto(@PathVariable Long id) {
        notaService.removeProduto(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/pagamento")
    public ResponseEntity adicionaPagamento(@RequestBody @Valid DadosDuplicata dadosDuplicata ){
        notaService.adicionaPagamento(dadosDuplicata);
        return ResponseEntity.ok(notaService.retornaValorPago());
    }
    @GetMapping("/pegaPagamentosAoRegarregar")
    public ResponseEntity getPagamentosEfetuados() {
        return ResponseEntity.ok(notaService.retornaValorPago());
    }
    @PatchMapping("/removeParcela/{numParcela}")
    public ResponseEntity removerPagamentoDaNota( @PathVariable int numParcela) {
        notaService.removerPagamento(numParcela);
        return ResponseEntity.ok(notaService.retornaValorPago());
    }
    @PostMapping("/finalizar")
    public ResponseEntity CadastraNota(@RequestBody @Valid DadosCabecalhoNota dadosCabecalhoNota) {
        return ResponseEntity.ok(notaService.finalizaNota(dadosCabecalhoNota));
    }
}



