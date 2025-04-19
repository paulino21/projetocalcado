package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCabecalhoNota(
        @NotNull(message = "O fornecedor é obrigatorio")
        Long idFornecedor,
        @NotNull(message = "data é obrigatorio")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        @NotNull(message = "o nemeto da tona nao pode ser nulo")
        @Min(1)
        Long numeroNota ) {
}
