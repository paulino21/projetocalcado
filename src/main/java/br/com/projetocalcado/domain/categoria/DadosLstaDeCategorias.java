package br.com.projetocalcado.domain.categoria;

import br.com.projetocalcado.domain.produto.Produto;

import java.util.List;

public record DadosLstaDeCategorias(

        Long id,
        String nome,
        List<Long> produtosIds

) {
    public DadosLstaDeCategorias(Categoria categoria) {
        this(categoria.getId(), categoria.getNome(),
                categoria.getProduto().stream().map(Produto::getId).toList());
    }
}
