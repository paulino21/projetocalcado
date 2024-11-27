package br.com.projetocalcado.domain.pagamentos;

import java.math.BigDecimal;
import java.util.List;

public record DadosRetornoPagamentoNota(
        List<DadosPagamentoEfetuadoNota> pagamentosEfetuadosNotas,
        BigDecimal totalPago) {
}
