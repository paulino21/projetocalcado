package br.com.projetocalcado.domain.pagamentoPedido;

import java.math.BigDecimal;

public record DadosPagamentoEfetuado(int numPgto,Integer numParcela, String formaPagamento, BigDecimal valor) {
}
