package br.com.projetocalcado.domain.estoque;

import jakarta.validation.constraints.NotNull;

public record DadosEstoque(
        @NotNull
        Long id,
        Integer quantidade) {
    public DadosEstoque(Estoque estoque){
    this(estoque.getId(), estoque.getQuantidade());
    }


}
