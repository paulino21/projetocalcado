package br.com.projetocalcado.domain.pagamentos;

import java.math.BigDecimal;
import java.util.List;

public record DadosRetornoPagamento(
        List<DadosPagamentoEfetuado> pagamentosPedido,
         BigDecimal total
        ) {

}
