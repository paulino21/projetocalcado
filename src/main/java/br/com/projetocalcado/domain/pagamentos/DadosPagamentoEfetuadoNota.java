package br.com.projetocalcado.domain.pagamentos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosPagamentoEfetuadoNota(
        Integer numParcela,
        LocalDate dataVenc,
        BigDecimal valorDup,
        String descricaoPagamento

) {
}
