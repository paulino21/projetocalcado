package br.com.projetocalcado.domain.pagamentos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DadosPagamento(
        @NotNull
        Long idPagamento,
        @NotNull
        @Positive
        @Digits( integer = 10, fraction = 2)
        BigDecimal valor,
        @NotNull
        Integer numParcela) {
}
