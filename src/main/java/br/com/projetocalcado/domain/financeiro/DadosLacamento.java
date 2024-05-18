package br.com.projetocalcado.domain.financeiro;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosLacamento(
        @NotNull
        TipoLancamento tipoLancamento,
        @NotNull
        Long idTabLancamento,
        @NotBlank
        String descricao,
        @NotBlank
        String formaPto,
        @NotNull
        @Digits(integer = 10 , fraction = 2)
        BigDecimal valor,
        @FutureOrPresent
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataVencimento,
        @NotNull
        Boolean pago,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataPagamento
        ) {
}
