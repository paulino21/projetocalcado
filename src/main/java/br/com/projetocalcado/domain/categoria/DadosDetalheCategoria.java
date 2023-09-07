package br.com.projetocalcado.domain.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDetalheCategoria(
        @NotNull
        Long id,
        @NotBlank
        String nome) {


    public DadosDetalheCategoria(Categoria categoria){

        this(categoria.getId(), categoria.getNome());
    }

}
