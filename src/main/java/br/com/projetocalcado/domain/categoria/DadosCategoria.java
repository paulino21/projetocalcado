package br.com.projetocalcado.domain.categoria;

import jakarta.validation.constraints.NotBlank;

public record DadosCategoria(
        @NotBlank
        String nome
) {


}
