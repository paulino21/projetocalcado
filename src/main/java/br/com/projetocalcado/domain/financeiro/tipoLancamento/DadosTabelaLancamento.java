package br.com.projetocalcado.domain.financeiro.tipoLancamento;

import jakarta.validation.constraints.NotBlank;

public record DadosTabelaLancamento(

        @NotBlank
        String nome
) {
}
