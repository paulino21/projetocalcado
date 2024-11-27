package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDuplicata(
        @NotNull
        Long idFormaPagto,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataVenc,
        @NotNull
        BigDecimal valorDup) {
}
