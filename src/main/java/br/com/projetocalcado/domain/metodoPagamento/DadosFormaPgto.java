package br.com.projetocalcado.domain.metodoPagamento;

import jakarta.validation.constraints.NotBlank;

public record DadosFormaPgto(
        TipoPagamento tipoPagamento,
        @NotBlank
        String descricao) {
}
