package br.com.projetocalcado.domain.produto;

import jakarta.validation.constraints.NotNull;

public record DadosEanProduto(
        @NotNull
        String eanProduto

) {
}
