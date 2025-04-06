package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.CategoriaRepository;
import br.com.projetocalcado.domain.estoque.Estoque;
import br.com.projetocalcado.domain.estoque.EstoqueRepository;
import br.com.projetocalcado.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    EstoqueRepository estoqueRepository;
    public void deletaProduto(Long id){
            var produto = produtoRepository.getReferenceById(id);
            produtoRepository.delete(produto);
    }

    public Produto cadastraProduto (DadosCadastroProduto dadosProduto) {

        if(!categoriaRepository.existsById(dadosProduto.idCategoria())) {
            throw new ValidacaoException("Categoria não encontrada");
        }
        var categoria = categoriaRepository.getReferenceById(dadosProduto.idCategoria());
        var estoque = estoqueRepository.save( new Estoque(dadosProduto.quantidadeEstoque()));
        var produto = new Produto( dadosProduto.codProd(), dadosProduto.codEan(), dadosProduto.nomeProd(),
                dadosProduto.custoProd(), dadosProduto.precoVenda(), LocalDateTime.now(), categoria, estoque);
        estoque.adicionarProduto(produto);
        produtoRepository.save(produto);

        return produto;
    }
    public DadosDetalheDoproduto atualizaProduto(DadosProdutoAlterado dadosProdutoAlterado){

        var produto = produtoRepository.getReferenceById(dadosProdutoAlterado.id());
        var categoria = categoriaRepository.getReferenceById(dadosProdutoAlterado.idCategoria());
        var estoque = estoqueRepository.getReferenceById(produto.getEstoque().getId());
        estoque.setQuantidade(dadosProdutoAlterado.quantidadeEstoque());

            produto.setNomeProd(dadosProdutoAlterado.nomeProd());
            produto.setCodEan(dadosProdutoAlterado.codEan());
            produto.setCodProd(dadosProdutoAlterado.codProd());
            produto.setCustoProd(dadosProdutoAlterado.custoProd());
            produto.setPrecoVenda(dadosProdutoAlterado.precoVenda());
            produto.setCategoria(categoria);
            produto.setEstoque(estoque);

        return new DadosDetalheDoproduto(produto);

    }

    public void atualizaPrecoProduto(Long id, BigDecimal custoProd, BigDecimal precoVenda) {

        var produto = produtoRepository.getReferenceById(id);
        produto.setCustoProd(custoProd);
        produto.setPrecoVenda(precoVenda);
        produtoRepository.save(produto);
    }
}
