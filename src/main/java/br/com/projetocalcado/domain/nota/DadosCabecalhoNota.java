package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCabecalhoNota(
        @NotNull
        Long idFornecedor,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        @NotNull
        @Min(1)
        Long numeroNota ) {
}
