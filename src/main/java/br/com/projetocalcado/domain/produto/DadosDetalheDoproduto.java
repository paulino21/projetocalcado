package br.com.projetocalcado.domain.produto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosDetalheDoproduto(
        @NotNull
        Long id,
        @NotBlank
        String codProd,
        @NotBlank
        String codEan,
        @NotBlank
        String nomeProd,
        @NotNull
        @Digits(integer = 10 , fraction = 2)
        BigDecimal precoVenda,
        @Digits(integer = 10 , fraction = 2)
        BigDecimal custoProd,
        @NotNull
        Long idCategoria
) {
    public DadosDetalheDoproduto(Produto produto) {
        this(produto.getId(), produto.getCodProd(),produto.getCodEan(), produto.getNomeProd(),
                produto.getPrecoVenda(), produto.getCustoProd() , produto.getCategoria().getId());
    }
}
