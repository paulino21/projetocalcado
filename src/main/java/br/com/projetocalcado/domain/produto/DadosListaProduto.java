package br.com.projetocalcado.domain.produto;

import java.math.BigDecimal;

public record DadosListaProduto(Long id, String codProd, String codEan, String nomeProd , BigDecimal custoProd ) {

    public DadosListaProduto(Produto produto){
        this(produto.getId(), produto.getCodProd(), produto.getCodEan(),produto.getNomeProd(), produto.getCustoProd());
    }
}
