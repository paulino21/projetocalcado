package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;

import java.math.BigDecimal;

public record DadosCadastroProduto(String codProd, String codEan, String nomeProd, BigDecimal custoProd, Categoria categoria) {

}
