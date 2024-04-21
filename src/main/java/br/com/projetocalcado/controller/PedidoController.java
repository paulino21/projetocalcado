package br.com.projetocalcado.controller;

import br.com.projetocalcado.domain.Pedido.PedidoService;
import br.com.projetocalcado.domain.pagamentoPedido.DadosPagamento;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;
    @GetMapping("/cliente/{id}")
    public ResponseEntity adicionaClienteDoPedido(@PathVariable Long id) {
        pedidoService.buscaCliente(id);
        return ResponseEntity.ok(pedidoService.buscaCliente(id));
    }
    @GetMapping("/{ean}/{quantidade}")
    public ResponseEntity adicionaProduto(@PathVariable String ean, @PathVariable Integer quantidade) {
        return ResponseEntity.ok(pedidoService.adicionaProdutoPedido(ean, quantidade));
    }
    @GetMapping("/adicionaQuantidade/{id}/{acao}")
    public ResponseEntity adicionaQteProdutoPedido(@PathVariable Long id, @PathVariable String acao) {
        pedidoService.alteraQuantidadeItens(id , acao);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/removeProduto/{id}")
    public ResponseEntity removeProduto(@PathVariable Long id) {
        pedidoService.removeProduto(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/pagamento")
    public ResponseEntity adicionaPagamento(@RequestBody @Valid DadosPagamento dadosPagamento ){
        pedidoService.adicionaPagamento(dadosPagamento);
        return ResponseEntity.ok(pedidoService.retornaValorPago());
    }
    @GetMapping("/finalizar")
    public ResponseEntity CadastraPedido() {
       return ResponseEntity.ok(pedidoService.finalizaPedido());
    }
}
