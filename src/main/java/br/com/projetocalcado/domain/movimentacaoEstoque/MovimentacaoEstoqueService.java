package br.com.projetocalcado.domain.movimentacaoEstoque;

import br.com.projetocalcado.domain.produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoEstoqueService {

    @Autowired
    MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    public void registraEntradaMovimentacao(Produto produto ,TipoMovimentacao tipoMovimentacao, Integer quantidade){

        var movimentacaoEstoque = new MovimentacaoEstoque(produto, tipoMovimentacao, quantidade);
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }



}
