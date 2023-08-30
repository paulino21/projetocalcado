package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;

import java.math.BigDecimal;

public record DadosDetalheDoproduto(Long id, String codProd, String codEan, String nomeProd, BigDecimal custoProd, Categoria categoria) {
    public DadosDetalheDoproduto(Produto produto) {
        this(produto.getId(), produto.getCodProd(), produto.getCodEan(), produto.getNomeProd(),
                produto.getCustoProd() , produto.getCategoria());
    }
}
