package br.com.projetocalcado.domain.metodoPagamentoPedido;

import jakarta.validation.constraints.NotBlank;

public record DadosFormaPgto(
        TipoPagamento tipoPagamento,
        @NotBlank
        String descricao) {
}
