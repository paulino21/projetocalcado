package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCabecalhoNota(
        @NotNull(message = "Fornecedor obrigatório")
        Long idFornecedor,
        @NotNull(message = "Data da nota obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        @NotNull(message = "Número da nota obrigatório")
        @Min(1)
        Long numeroNota ) {
}
