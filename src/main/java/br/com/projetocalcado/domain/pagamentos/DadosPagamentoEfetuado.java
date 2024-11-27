package br.com.projetocalcado.domain.pagamentos;

import java.math.BigDecimal;

public record DadosPagamentoEfetuado(int numPgto,Integer numParcela, String formaPagamento, BigDecimal valor) {
}
