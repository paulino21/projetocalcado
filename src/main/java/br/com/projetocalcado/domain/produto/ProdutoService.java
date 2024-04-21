package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.CategoriaRepository;
import br.com.projetocalcado.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    public void deletaProduto(Long id){
            var produto = produtoRepository.getReferenceById(id);
            produtoRepository.delete(produto);
    }

    public Produto cadastraProduto (DadosCadastroProduto dadosProduto) {

        if(!categoriaRepository.existsById(dadosProduto.idCategoria())) {
            throw new ValidacaoException("Categoria não encontrada");
        }
        var categoria = categoriaRepository.getReferenceById(dadosProduto.idCategoria());
        var produto = new Produto( dadosProduto.codProd(), dadosProduto.codEan(), dadosProduto.nomeProd(),
                dadosProduto.custoProd(), dadosProduto.precoVenda(), LocalDateTime.now(), categoria);
        produtoRepository.save(produto);

        return produto;
    }
    public DadosDetalheDoproduto atualizaProduto(DadosDetalheDoproduto dadosProduto){

        var produto = produtoRepository.getReferenceById(dadosProduto.id());
        var categoria = categoriaRepository.getReferenceById(dadosProduto.idCategoria());

            produto.setNomeProd(dadosProduto.nomeProd());
            produto.setCodEan(dadosProduto.codEan());
            produto.setCodProd(dadosProduto.codProd());
            produto.setCustoProd(dadosProduto.custoProd());
            produto.setCustoProd(dadosProduto.custoProd());
            produto.setCategoria(categoria);

        return new DadosDetalheDoproduto(produto);

    }
}
