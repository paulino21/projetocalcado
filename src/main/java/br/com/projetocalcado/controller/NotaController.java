package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.nota.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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
    @PostMapping
    @Transactional
    public ResponseEntity cadastraNota(@RequestBody @Valid DadosNotaFiscal dadosNotaFiscal , UriComponentsBuilder uriBuilder){
            var nota = notaService.cadastraNota(dadosNotaFiscal);
            var uri = uriBuilder.path("/nota/{id}").buildAndExpand(nota.id()).toUri();
        return ResponseEntity.created(uri).body(nota);
    }
    @GetMapping("/cabecalhoNota")
    public ResponseEntity trataCabecalhoNota(@RequestBody @Valid DadosCabecalhoNota dadosCabecalhoNota) {
        return ResponseEntity.ok(notaService.trataCabecalhoNota(dadosCabecalhoNota));
    }
    @GetMapping("/{ean}/{quantidade}")
    public ResponseEntity adicionaProduto(@PathVariable String ean, @PathVariable Integer quantidade) {
        return ResponseEntity.ok(notaService.adicionaProdutoNota(ean, quantidade));
    }
    @GetMapping("/adicionaQuantidade/{id}/{acao}")
    public ResponseEntity adicionaQteProdutoPedido(@PathVariable Long id, @PathVariable String acao) {
        notaService.alteraQuantidadeItens(id , acao);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/removeProduto/{id}")
    public ResponseEntity removeProduto(@PathVariable Long id) {
        notaService.removeProduto(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/pagamento")
    public ResponseEntity adicionaPagamento(@RequestBody @Valid DadosDuplicata dadosDuplicata ){
        notaService.adicionaPagamento(dadosDuplicata);
        return ResponseEntity.ok(notaService.retornaValorPago());
    }
    @GetMapping("/finalizar")
    public ResponseEntity CadastraPedido() {
        return ResponseEntity.ok(notaService.finalizaNota());
    }
}



