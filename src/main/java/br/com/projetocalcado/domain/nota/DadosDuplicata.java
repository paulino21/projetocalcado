package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDuplicata(
        @NotNull(message = "Selecione a forma de pagamneto.")
        Long idFormaPagto,
        @NotNull(message = "Informe a data de vencimento.")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataVenc,
        @NotNull(message = "Informe o valor do pagamento.")
        BigDecimal valorDup) {
}
